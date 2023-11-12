package com.Chess_API;

import java.util.Map;

import static java.lang.Math.abs;

public class Bishop extends ChessPiece{
    public Bishop(Color color) {
        super(color, PieceType.BISHOP);
    }
    @Override
    public boolean validMove(Cell FromCell, Cell ToCell, Map<Cell, ChessPiece> boardState) {
        if(abs(FromCell.getY() - ToCell.getY()) != abs(FromCell.getX()) - ToCell.getX())
            return false;
        for (Map.Entry<Cell, ChessPiece> entry : boardState.entrySet()) {
            Cell cell = entry.getKey();
            ChessPiece piece = entry.getValue();
            if(abs(FromCell.getY() - cell.getY()) != abs(FromCell.getX()) - cell.getX()) // checking if there is a piece on the way
                return false;
        }
        return true;
    }
}
