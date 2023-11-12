package com.Chess_API;

import java.util.Map;

public final class Pawn extends ChessPiece {
    public Pawn(Color color) {
        super(color, PieceType.PAWN);
    }

    @Override
    public boolean validMove(Cell fromCell, Cell toCell, Map<Cell, ChessPiece> boardState) {
        if (this.first_move && toCell.getX() == fromCell.getX()) {
            if ((this.color == Color.BLACK && toCell.getY() - fromCell.getY() <= 2) ||
                    (this.color == Color.WHITE && toCell.getY() - fromCell.getY() >= -2)) {
                return true;
            }
            return false;
        }
        return false;
    }
}