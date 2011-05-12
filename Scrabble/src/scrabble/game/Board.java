package scrabble.game;

import java.util.Vector;
import java.lang.Math;
import scrabble.Constants;

class Position{
    public int x;
    public int y;
    public Position (int _x, int _y)
    {
        x = _x;
        y = _y;
    }
    public Position (Position pos)
    {
        x = pos.x;
        y = pos.y;
    }
}

public class Board {
    private Square board[][];
    private static final int size = 15;
    private Vector <String> wordsToCheck;
    private Vector <Position> initPos;
    private static final int BOARD[][] = 
    	{{4, 0, 0, 1, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 4,},
    	 {0, 3, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 3, 0,},
    	 {0, 0, 3, 0, 0, 0, 1, 0, 1, 0, 0, 0, 3, 0, 0,},
    	 {1, 0, 0, 3, 0, 0, 0, 1, 0, 0, 0, 3, 0, 0, 1,},
    	 {0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0,},
    	 {0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0,},
    	 {0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0,},
    	 {4, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 4,},
    	 {0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0,},
    	 {0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0,},
    	 {0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0,},
    	 {1, 0, 0, 3, 0, 0, 0, 1, 0, 0, 0, 3, 0, 0, 1,},
    	 {0, 0, 3, 0, 0, 0, 1, 0, 1, 0, 0, 0, 3, 0, 0,},
    	 {0, 3, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 3, 0,},
    	 {4, 0, 0, 1, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 4,}
    	};
    
    
    public Board()
    {
    	board = new Square[size][size];
    	for (int i = 0; i < size; i++)
    		for (int j = 0; j < size; j++)
    			board[i][j] = new Square(BOARD[i][j]);
    }

    // get a square
    public Square getSquare(int i, int j)
    {
    	return board[i][j];
    }

    // update a move to the board
    public void update(Vector<LetterMove> move)
    {
        for (int i=0; i<move.size(); i++)
        {
            board[move.elementAt(i).x][move.elementAt(i).y].tile = move.elementAt(i).tile;
        }
    }
    //find the min X if word is vertical, min Y if word is horizontal
    private int findMin (Vector <LetterMove> currentMove)
    {
        int min = 0;
        if (currentMove.elementAt(0).x == currentMove.elementAt(1).x)
        {
            min = currentMove.elementAt(0).y;
            for (int i=1; i< currentMove.size(); i++)
            {
                if (currentMove.elementAt(i).y < min) min = currentMove.elementAt(i).y;
            }
        }
        else
        {
            min = currentMove.elementAt(0).x;
            for (int i=1; i< currentMove.size(); i++)
            {
                if (currentMove.elementAt(i).x < min) min = currentMove.elementAt(i).x;
            }
        }
        return min;
    }
    //find max min X if word is vertical, max Y if word is horizontal
    private int findMax (Vector <LetterMove> currentMove)
    {
        int max = 0;
        if (currentMove.elementAt(0).x == currentMove.elementAt(1).x)
        {
            max = currentMove.elementAt(0).y;
            for (int i=1; i< currentMove.size(); i++)
            {
                if (currentMove.elementAt(i).y > max) max = currentMove.elementAt(i).y;
            }
        }
        else
        {
            max = currentMove.elementAt(0).x;
            for (int i=1; i< currentMove.size(); i++)
            {
                if (currentMove.elementAt(i).x > max) max = currentMove.elementAt(i).x;
            }
        }
        return max;
    }
    //check if the NEW tiles is in one row or one column
    private int isOneLine(Vector<LetterMove> currentMove)
    {
        if (currentMove.size() == 2)
        {
            if (currentMove.elementAt(0).x == currentMove.elementAt(1).x 
                && Math.abs(currentMove.elementAt(0).y - currentMove.elementAt(1).y) ==1 ) 
            {
                return 1;//horizontal
            }
            if (currentMove.elementAt(0).y == currentMove.elementAt(1).y
                && Math.abs(currentMove.elementAt(0).x - currentMove.elementAt(1).x) ==1)
            {
                return 2;//vertical
            }
            return 0;//not one line
        }
        //more than 2 tiles in a move
        if (currentMove.elementAt(0).x == currentMove.elementAt(1).x)
        {
            for (int i=2; i<currentMove.size(); i++)
            {
                if (currentMove.elementAt(i).x != currentMove.elementAt(0).x) return 0;
            }
            for (int i = findMin(currentMove); i<=findMax(currentMove); i++)
            {
                if (!board [currentMove.elementAt(0).x][i].isOccupied()) return 0;
            }
            return 1;
        }
        else if (currentMove.elementAt(0).y == currentMove.elementAt(1).y)
        {
            for (int i=2; i<currentMove.size(); i++)
            {
                if (currentMove.elementAt(i).y != currentMove.elementAt(0).y) return 0;
            }
            for (int i = findMin(currentMove); i<=findMax(currentMove); i++)
            {
                if (!board [i][currentMove.elementAt(0).y].isOccupied()) return 0;
            }
            return 2;
        }
        else return 0;
    }
    //make the word correspoding to the main direction 
    private String makeMainWord (Vector<LetterMove> currentMove)
    {
        String s = "";
        if (currentMove.size() == 1)
        {
            String tmp = "";
            int i = currentMove.elementAt(0).y - 1;
            while (i >= 0 && board[currentMove.elementAt(0).x][i].isOccupied())
            {
                tmp += board[currentMove.elementAt(0).x][i].tile.letter;
                if ((i>0 && 
                    !board[currentMove.elementAt(0).x][i-1].isOccupied()) || i==0) 
                {
                    Position p = new Position (currentMove.elementAt(0).x, i);
                    initPos.add(p);
                }
                i--;
            }
            for (int j=tmp.length()-1; j>=0; j--)
            {
                s += tmp.charAt(j);
            }
            int j = currentMove.elementAt(0).x;
            while (j<size && board[currentMove.elementAt(0).x][j].isOccupied())
            {
                s += board[currentMove.elementAt(0).x][j].tile.letter;
                j++;
            }
        }
        else
        {
            if (currentMove.elementAt(0).x == currentMove.elementAt(1).x)
            {
                String tmp = "";
                int it = findMin(currentMove) - 1;
                while (it >= 0 && board[currentMove.elementAt(0).x][it].isOccupied())
                {
                    tmp += board[currentMove.elementAt(0).x][it].tile.letter;
                    if ((it>0 && 
                    !board[currentMove.elementAt(0).y][it-1].isOccupied()) || it==0) 
                    {
                        Position p = new Position (currentMove.elementAt(0).x, it);
                        initPos.add(p);
                    }
                    it--;
                }
                for (int j=tmp.length()-1; j>=0; j--)
                {
                    s += tmp.charAt(j);
                }
                for (int i= findMin(currentMove); i<=findMax(currentMove); i++)
                {
                    s += board[currentMove.elementAt(0).x][i].tile.letter;
                }
                int j = currentMove.elementAt(0).y+1;
                while (j<size && board[j][currentMove.elementAt(0).y].isOccupied())
                {
                    s += board[currentMove.elementAt(0).x][j].tile.letter;
                    j++;
                }
            }
            else
            {
                String tmp = "";
                int it = findMin(currentMove) - 1;
                while (it >= 0 && board[it][currentMove.elementAt(0).y].isOccupied())
                {
                    tmp += board[it][currentMove.elementAt(0).y].tile.letter;
                    if ((it>0 && 
                        !board[it-1][currentMove.elementAt(0).y].isOccupied()) || it==0) 
                    {
                        Position p = new Position (it, currentMove.elementAt(0).y);
                        initPos.add(p);
                    }
                    it--;
                }
                for (int j=tmp.length()-1; j>=0; j--)
                {
                    s += tmp.charAt(j);
                }
                for (int i= findMin(currentMove); i<=findMax(currentMove); i++)
                {
                    s += board[i][currentMove.elementAt(0).y].tile.letter;
                }
                int j = currentMove.elementAt(0).x+1;
                while (j<size && board[j][currentMove.elementAt(0).y].isOccupied())
                {
                    s += board[j][currentMove.elementAt(0).y].tile.letter;
                    j++;
                }
            }
        }
        return s;
    }
    //add the secondary word to the vector
    private void makeSecondaryWord (Vector<LetterMove> m)
    {
        String s="";
        //vertical
        if (isLine(m) == 1)
        {
            for (int i=0; i<m.size(); i++)
            {
                String tmp = "";
                int it = m.elementAt(0).x - 1;
                while (it >= 0 && board[it][m.elementAt(i).y].isOccupied())
                {
                    tmp += board[it][m.elementAt(i).y].tile.letter;
                    if ((it>0 && 
                        !board[it-1][m.elementAt(i).y].isOccupied()) || it==0) 
                    {
                        Position p = new Position (it, m.elementAt(i).y);
                        initPos.add(p);
                    }
                    it--;
                }
                for (int j=tmp.length()-1; j>=0; j--)
                {
                    s += tmp.charAt(j);
                }
                int j = m.elementAt(i).y;
                while (j<size && board[m.elementAt(i).x][j].isOccupied())
                {
                    s += board[j][m.elementAt(i).y].tile.letter;
                    j++;
                }
            }
            wordsToCheck.add(s);
        }
        //horizontal
        else if (isLine(m) == 2)
        {
            for (int i=0; i<m.size(); i++)
            {
                String tmp = "";
                int it = m.elementAt(i).y - 1;
                while (i >= 0 && board[m.elementAt(i).x][it].isOccupied())
                {
                    tmp += board[m.elementAt(i).x][it].tile.letter;
                    if ((it>0 && 
                        !board[m.elementAt(i).x][it-1].isOccupied()) || it==0) 
                    {
                        Position p = new Position (m.elementAt(i).x, it);
                        initPos.add(p);
                    }
                    it--;
                }
                for (int j=tmp.length()-1; j>=0; j--)
                {
                    s += tmp.charAt(j);
                }
                int j = m.elementAt(0).y;
                while (j<size && board[m.elementAt(i).x][j].isOccupied())
                {
                    s += board[m.elementAt(i).x][j].tile.letter;
                    j++;
                }
            }
            wordsToCheck.add(s);
        }
    }
    public Vector<String> getWords (Vector <LetterMove> currentMove)
    {
        wordsToCheck.add(makeMainWord(currentMove)); 
        makeSecondaryWord(currentMove);
        return wordsToCheck;
    }
    public Vector <Position> getPos (Vector<LetterMove> currentMove)
    {
        return initPos;
    }
    public boolean checkNewLetter (Position p)
    {
        if (board[p.x][p.y].isOccupied()) return false;
        return true;
    }
    //check if the new tiles connect with old tiles
    public boolean checkConnected (Vector <LetterMove> m)
    {
        if (m.size() == 1)
        {
            if (m.elementAt(0).x > 0)
            {
                if (!checkNewLetter(new Position(m.elementAt(0).x-1, m.elementAt(0).y))) return true;
            }
            if (m.elementAt(0).x < 14)
            {
                if (!checkNewLetter(new Position(m.elementAt(0).x+1, m.elementAt(0).y))) return true;
            }
            if (m.elementAt(0).y > 0)
            {
                if (!checkNewLetter(new Position(m.elementAt(0).x, m.elementAt(0).y-1))) return true;
            }
            if (m.elementAt(0).y < 14)
            {
                if (!checkNewLetter(new Position(m.elementAt(0).x, m.elementAt(0).y+1))) return true;
            }
        }
        else
        {
            if (isOneLine(m) == 1)
            {
                int start, end;
                if (findMin(m) > 0) start = findMin(m)-1;
                else start = 0;
                if (findMax(m) > 0) end = findMax(m)-1;
                else end = size-1;
                while (start <=end)
                {
                    if (!checkNewLetter(new Position(m.elementAt(0).x, start))) return true;
                    start ++;
                }
            }
            else
            {
                int start, end;
                if (findMin(m) > 0) start = findMin(m)-1;
                else start = 0;
                if (findMax(m) > 0) end = findMax(m)-1;
                else end = size-1;
                while (start <=end)
                {
                    if (!checkNewLetter(new Position(start, m.elementAt(0).y))) return true;
                    start ++;
                }
            }
        }
        return false;
    }
    public int isLine (Vector <LetterMove> currentMove)
    {
        if (isOneLine(currentMove) == 0) return 0;
        else
        {
            if (checkConnected(currentMove)) return isOneLine(currentMove);//if connect
            else return 0;//not connect
        }
    }
}
