package com.Chess_API;
import java.util.Map;
public class Rook extends ChessPiece{
    public Rook(Color color) {
        super(color, PieceType.ROOK);
    }
    public boolean ontheWay(Cell fromCell, Cell toCell, Cell cell) {
        if((cell.getY() == fromCell.getY() && fromCell.getY() == toCell.getY()) ||
                (cell.getX() == fromCell.getX() && fromCell.getX() == toCell.getX()))
            return true;
        return false;
    }
    @Override
    public boolean validMove(Cell fromCell, Cell toCell, Map<Cell, ChessPiece> boardState) {
        if(fromCell.getX() != toCell.getX() && fromCell.getY() != toCell.getY())
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
