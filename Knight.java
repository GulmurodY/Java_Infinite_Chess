package com.Chess_API;

import java.util.Map;

public class Knight extends ChessPiece{
        public enum Direction {
            UP_LEFT(-1, 2),
            UP_RIGHT(1, 2),
            RIGHT_UP(2, 1),
            RIGHT_DOWN(2, -1),
            DOWN_RIGHT(1, -2),
            DOWN_LEFT(-1, -2),
            LEFT_DOWN(-2, -1),
            LEFT_UP(-2, 1);

            private final int x;
            private final int y;

            Direction(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public int getX() {
                return x;
            }

            public int getY() {
                return y;
            }
        }



    public Knight(Color color) {
        super(color, PieceType.KNIGHT);
    }

    @Override
    public boolean  validMove(Cell fromCell, Cell toCell, Map<Cell, ChessPiece> boardState) {
        for (Direction direction : Direction.values()) {
            if(fromCell.getX() + direction.getX() == toCell.getX() && fromCell.getY() + direction.getY() == toCell.getY())
                return true;
        }
        return false;
    }
}
