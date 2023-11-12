package com.Chess_API;

import java.util.Map;

import static java.lang.Math.abs;

public class Queen extends ChessPiece{
    public Queen(Color color) {
        super(color, PieceType.QUEEN);
    }

    public boolean ontheWay(Cell fromCell, Cell toCell, Cell cell) {
        if((cell.getY() == fromCell.getY() && fromCell.getY() == toCell.getY()) ||
                (cell.getX() == fromCell.getX() && fromCell.getX() == toCell.getX()) ||
                abs(fromCell.getY() - cell.getY()) != abs(fromCell.getX()) - cell.getX())
            return true;
        return false;
    }

    @Override
    public boolean validMove(Cell fromCell, Cell toCell, Map<Cell, ChessPiece> boardState) {
        if(fromCell.getX() != toCell.getX() && fromCell.getY() != toCell.getY() &&
                abs(fromCell.getY() - toCell.getY()) != abs(fromCell.getX()) - toCell.getX())
            return false;
        for (Map.Entry<Cell, ChessPiece> entry : boardState.entrySet()) {
            Cell cell = entry.getKey();
            ChessPiece piece = entry.getValue();
            if(ontheWay(fromCell, toCell, cell)) // checking if there is a piece on the way
                return false;
        }
        return true;
    }

}
