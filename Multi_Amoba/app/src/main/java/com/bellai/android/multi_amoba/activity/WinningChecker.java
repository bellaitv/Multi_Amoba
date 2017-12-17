package com.bellai.android.multi_amoba.activity;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by adam.bellai on 2016. 12. 25..
 */
public class WinningChecker {

    private static final String TAG = WinningChecker.class.getName();

    public static final int CONTINUE = -1;
    public static final int CLIENT_WINS = 0;
    public static final int SERVER_WINS = 1;
    public static final int DRAW = 2;

    private ImageView board[][];

    public WinningChecker(ImageView[][] board) {
        this.board = board;
    }

    public int check(int sign) {
        int rows = checkRows(sign);
        if (rows == sign) {
            Log.d(TAG, "5 on rows ");
            return sign;
        }
        int cols = checkCols(sign);
        if (cols == sign) {
            Log.d(TAG, "5 on cols");
            return sign;
        }
        int diagonal = checkDiagonal(sign);
        if (diagonal == sign) {
            Log.d(TAG, "5 on diagonal");
            return sign;
        }
        if (!isFull())
            return CONTINUE;
        else return DRAW;
    }

    private boolean isFull() {
        //todo ez így nem jó
        boolean result = true;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                IDObject tag = (IDObject) board[i][j].getTag();
                if (tag != null) {
                    result = false;
                }
            }
        }
        return result;
    }

    private int checkDiagonal(int sign) {
        int sum = checkRightDiagonal(sign);
        if (sum != 0)
            sum = checkLeftDiagonal(sign);
        return sum;
    }

    private int checkRightDiagonal(int sign) {
        int sum = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                IDObject idObject = (IDObject) board[i][j].getTag();
                if (idObject != null)
                    if (idObject.getSign() == sign) {
                        sum++;
                        //check others
                        for (int k = 1; k <= 5; k++) {
                            if ((i + k <= board.length) && (j - k >= 0)) {
                                IDObject object = (IDObject) board[i + k][j - k].getTag();
                                if (object != null && object.getSign() == sign) {
                                    sum++;
                                    Log.d(TAG, String.format("right sum is %d", sum));
                                } else {
                                    sum = 0;
                                    break;
                                }
                                if (sum == 5) {
                                    Log.d(TAG, "5 on checkLeftDiagonal");
                                    return sign;
                                }
                            }
                        }
                    } else sum = 0;
                else sum = 0;
            }
        }
        return 1;
    }

    /*private int checkLeftDiagonal(int sign) {
        int sum = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                IDObject idObject = (IDObject) board[i][j].getTag();
                if (idObject != null)
                    if (idObject.getSign() == sign) {
                        sum++;
                        for (int k = 1; k < 5; k++)
                            if (board.length >= i + k && board.length >= j - k &&
                                    (j - k) >= 0) {
                                IDObject idObject1 = (IDObject) board[i + k][j - k].getTag();
                                if (idObject1 != null)
                                    if (idObject1.getSign() == sign)
                                        sum++;
                            } else {
                                sum = 0;
                                break;
                            }
                    }
                if (sum == 5) {
                    IDObject idObject1 = new IDObject(0,0);
                    Log.d(TAG, "5 on checkLeftDiagonal");
                    return sign;
                }
            }
        }
        return 1;
    }*/

    private int checkLeftDiagonal(int sign) {
        int sum = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                IDObject idObject = (IDObject) board[i][j].getTag();
                if (idObject != null)
                    if (idObject.getSign() == sign) {
                        sum++;
                        //check others
                        for (int k = 1; k <= 5; k++) {
                            IDObject object = (IDObject) board[i + k][j + k].getTag();
                            if (object != null && object.getSign() == sign) {
                                sum++;
                                Log.d(TAG, String.format("left sum is %d", sum));
                            } else {
                                sum = 0;
                                break;
                            }
                            if (sum == 5) {
                                Log.d(TAG, "5 on checkLeftDiagonal");
                                return sign;
                            }
                        }
                    } else sum = 0;
                else sum = 0;
            }
        }
        return 1;
    }

    private int checkCols(int sign) {
        int sum = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                IDObject idObject = (IDObject) board[j][i].getTag();
                if (idObject != null && idObject.getSign() == sign)
                    sum++;
                else
                    sum = 0;
                if (sum == 5)
                    return sign;
            }
            sum = 0;
        }
        return 1;
    }

    private int checkRows(int sign) {
        int sum = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                IDObject idObject = (IDObject) board[i][j].getTag();
                if (idObject != null && idObject.getSign() == sign)
                    sum++;
                else
                    sum = 0;
                if (sum == 5)
                    return sign;
            }
            sum = 0;
        }
        return 1;
    }
}
