package com.Chess_API;


import java.util.*;


class ChessBoard {
    private final Map<Cell, ChessPiece> boardState;
    private final List<Cell> megaPawnCells;
    private Color currentTurn;

    public ChessBoard(List<Cell> megacells) {
        // initialize the board and megaPawnCells
        this.boardState = new HashMap<>();
        this.megaPawnCells = megacells;
        // initialize other parameters
    }
    public void addChessPiece(PieceType type, Cell cell, Color color) {
        boardState.put(cell, createPiece(type, cell, color));
    }

    public ChessPiece getChessPiece(Cell cell) {
        if (boardState.containsKey(cell)) {
            return boardState.get(cell);
        } else {
            throw new IllegalArgumentException("No piece found at the given cell.");
        }
    }
    public void moveChessPiece(Cell fromCell, Cell toCell) {
        if (!boardState.containsKey(fromCell)) {
            throw new IllegalArgumentException("No piece on the source cell.");
        }

        ChessPiece curr_piece = getChessPiece(fromCell);

        if (getChessPiece(toCell).color == curr_piece.color) {
            throw new IllegalArgumentException("Illegal move a piece of the same color is on the way.");
        }

        if (!curr_piece.validMove(fromCell, toCell, this.boardState)) {
            throw new IllegalArgumentException("Illegal move for this piece.");
        }

        if (notifyCheck(currentTurn) && curr_piece.type != PieceType.KING) {
            ChessPiece piece = boardState.remove(fromCell);
            boardState.put(toCell, piece);
            if (piece.type == PieceType.PAWN) {
                for (Cell c : this.megaPawnCells) {
                    if (c.equals(toCell)) {
                        piece.type = PieceType.MEGAPAWN;
                        break;
                    }
                }
            }
            if (notifyCheck(currentTurn)) {
                // Undo the move and throw an exception
                boardState.remove(toCell);
                boardState.put(fromCell, piece);
                System.out.println("Check! Save your King!");
            }
        }

        // Handle exceptions for invalid moves or checks/checkmates
        ChessPiece piece = boardState.remove(fromCell);
        boardState.put(toCell, piece);
        if (piece.type == PieceType.PAWN) {
            for(Cell c: this.megaPawnCells) {
                if (c == toCell) {
                    piece.type = PieceType.MEGAPAWN;
                    break;
                }
            }
        }
        currentTurn = (currentTurn == Color.BLACK) ? Color.WHITE : Color.BLACK;
        if(notifyCheck(currentTurn)) {
            if(notifyCheckmate(currentTurn))
                throw new IllegalArgumentException("You have lost the game(");
            else
                System.out.println("Check! Save your King!");
        }
    }


    private static ChessPiece createPiece(PieceType type, Cell cell, Color color) {
        if (type == PieceType.MEGAPAWN) {
            throw new IllegalArgumentException("MEGAPAWN can't be added");
        }

        if (Objects.requireNonNull(type) == PieceType.KING) {
            return new King(color);
        } else if (type == PieceType.QUEEN) {
            return new Queen(color);
        } else if (type == PieceType.ROOK) {
            return new Rook(color);
        } else if (type == PieceType.BISHOP) {
            return new Bishop(color);
        } else if (type == PieceType.KNIGHT) {
            return new Knight(color);
        } else if (type == PieceType.PAWN) {
            return new Pawn(color);
        }
        throw new IllegalArgumentException("Unsupported piece type: " + type);
    }

    private ChessPiece containsKey(Cell cell) {
        ChessPiece piece;
        try {
            piece = getChessPiece(cell);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return piece;
    }
    private Map.Entry<Cell, ChessPiece> getKingPosition(Color color) {
        for (Map.Entry<Cell, ChessPiece> entry : boardState.entrySet()) {
            Cell cell = entry.getKey();
            ChessPiece piece = entry.getValue();
            if(piece.type == PieceType.KING && color == piece.color)
                return entry;
        }
        throw new IllegalArgumentException("there is no king with such color");
    }
    // checking for check O(N) where N is the number of piece on the board
    private boolean notifyCheck(Color color) {
        Cell kingPosition = getKingPosition(color).getKey();
        for (Map.Entry<Cell, ChessPiece> entry : boardState.entrySet()) {
            Cell cell = entry.getKey();
            ChessPiece piece = entry.getValue();
            if(piece.validMove(cell, kingPosition, this.boardState)) {
                return true;
            }
        }
        return false;
    }
    private boolean notifyCheckmate(Color color) {
        Map.Entry<Cell, ChessPiece> entry = getKingPosition(color);
        Cell kingcell = entry.getKey();
        King king = (King) entry.getValue();

        List<Cell> legalMoves = king.getKingMoves(kingcell, color, boardState);
        for (Cell cell : legalMoves) {
            ChessPiece capturedPiece = null;
            if (boardState.containsKey(cell)) {
                capturedPiece = boardState.get(cell);
                boardState.remove(cell);
            }
            boardState.put(cell, king);

            if (!notifyCheck(color)) {
                boardState.remove(cell);
                if (capturedPiece != null) {
                    boardState.put(cell, capturedPiece);
                }
                return false;
            }

            boardState.remove(cell);
            if (capturedPiece != null) {
                boardState.put(cell, capturedPiece);
            }
        }
        return true;
    }
    // method for checking turn order
    private void checkTurnOrder(Color expectedTurn) {
        if (currentTurn != expectedTurn) {
            throw new IllegalStateException("Wrong turn order. It's the turn for " + currentTurn + " pieces.");
        }
    }

}
