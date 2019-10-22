package app;

/**
 * @author yjn
 * @creatTime 2019/10/4 - 1:34
 */
public class Card {
    int type;
    int rank;

    public Card(int rank, int flower) {
        this.type = flower;
        this.rank = rank;
    }

    public Card(String cardString){
        cardString = cardString.toUpperCase();
        if (cardString.charAt(0) == '*')
            type = 2;
        else if (cardString.charAt(0) == '#')
            type = 3;
        else if (cardString.charAt(0) == '$')
            type = 4;
        else if (cardString.charAt(0) == '&')
            type = 1;

        switch (cardString.charAt(1)){
            case '2':
                rank = 1;
                break;
            case '3':
                rank = 2;
                break;
            case '4':
                rank = 3;
                break;
            case '5':
                rank = 4;
                break;
            case '6':
                rank = 5;
                break;
            case '7':
                rank = 6;
                break;
            case '8':
                rank = 7;
                break;
            case '9':
                rank = 8;
                break;
            case '1':
                rank = 9;
                break;
            case 'J':
                rank = 10;
                break;
            case 'Q':
                rank = 11;
                break;
            case 'K':
                rank = 12;
                break;
            case 'A':
                rank = 13;
                break;
            default:

        }

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {

        String s = "";
        switch (type){
            case 1:
                s += '&';
                break;
            case 2:
                s += '*';
                break;
            case 3:
                s += '#';
                break;
            case 4:
                s += '$';
                break;
            default:
        }
        switch (rank){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                s += String.valueOf(rank+1);
                break;
            case 10:
                s += 'J';
                break;
            case 11:
                s += 'Q';
                break;
            case 12:
                s += 'K';
                break;
            case 13:
                s += 'A';
            default:
        }
        return s;
    }
}
