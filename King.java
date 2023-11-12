package com.Chess_API;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

public class King extends ChessPiece {
    public King(Color color) {
        super(color, PieceType.KING);
    }

    @Override
    public boolean validMove(Cell fromCell, Cell toCell, Map<Cell, ChessPiece> boardState) {
        return abs(fromCell.getX() - toCell.getX()) <= 1 && abs(fromCell.getY() - toCell.getY()) <= 1;
    }
    // a method for getting all moves a king can make
    public List<Cell> getKingMoves(Cell currentCell, Color color, Map<Cell, ChessPiece> boardState) {
        List<Cell> legalMoves = new ArrayList<>();
        int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };
        for (int i = 0; i < 8; i++) {
            int nextX = currentCell.x + dx[i];
            int nextY = currentCell.y + dy[i];
            if(boardState.get(new Cell(nextX, nextY)).color == color)    // since king cannot eat his own pieces
                continue;
            legalMoves.add(new Cell(nextX, nextY));
        }
        return legalMoves;
    }
}
