package com.Chess_API;

import java.util.Map;

public class ChessPiece {
    Color color;
    PieceType type;
    boolean first_move;

    public ChessPiece(Color color, PieceType type) {
        this.color = color;
        this.type = type;
        first_move = true;
    }
// valid move is implemented for every type in respective subclasses
    public boolean validMove(Cell fromCell, Cell toCell, Map<Cell, ChessPiece> boardState) {
        return false;
    }
}
