

import java.util.ArrayList;

public class State implements Comparable<State> {
    private static int N;
    private static int N_1;
    private static int M;
    private int cannibals;
    private int cannibalsRight;
    private int missionaries;
    private int missionariesRight;
    private char boat;
    private int cost;
    private State father=null;
    int f;
    int g;



    public State(char boat,int N,int M,int N_1) {
        this.N=N;
        this.M=M;
        this.N_1 = N_1;
        this.cannibals=N;
        this.missionaries=N_1;
        this.boat=boat;
        cannibalsRight=N-cannibals;
        missionariesRight=N_1-missionaries;

    }

    public State(int cannibals,int missionaries,char boat) {
        this.cannibals=cannibals;
        this.missionaries=missionaries;
        this.boat=boat;
        cannibalsRight=N-cannibals;
        missionariesRight=N_1-missionaries;
    }

    public State getFather() {
        return father;
    }

    public void setFather(State father) {
        this.father = father;
    }

    public boolean isValid() {
        return missionaries >= 0 && missionaries <= N_1
                && cannibals >= 0 && cannibals <= N
                && missionariesRight >= 0 && missionariesRight <= N_1
                && cannibalsRight >= 0 && cannibalsRight <= N
                && (missionaries == 0 || missionaries >= cannibals)
                && (missionariesRight == 0 || missionariesRight >= cannibalsRight)
                && ((boat == 'l' && missionaries + cannibals > 0)
                || (boat == 'r' && missionariesRight + cannibalsRight > 0));
    }


    public ArrayList<State> findValidStates() {
        ArrayList<State> children = new ArrayList<State>();
        if (boat == 'l') {
            addValidStates(children, 'r');
        } else {
            addValidStates(children, 'l');
        }
        return children;
    }

    private void addValidStates(ArrayList<State> children, char direction) {
        for (int i = 0; i <= M; i++) {
            for (int j = 0; j <= M; j++) {
                if (i + j > M || (i == 0 && j == 0) || (j < i && j != 0)) {
                    continue;
                }
                State temp = new State(cannibals + (direction == 'l' ? i : -i),
                        missionaries + (direction == 'l' ? j : -j),
                        direction);
                if (temp.isValid()) {
                    children.add(temp);
                }
            }
        }
    }

    public int calculateHeuristic(){
        if (boat=='r' && cannibals+missionaries > 0 ) cost = 2 * (cannibals+missionaries)/M;
        else if (boat=='l' && (cannibals+missionaries==M || cannibals+missionaries==1)) cost = 1;
        else if (cannibals+missionaries==0) cost = 0;
        return cost;
    }

    public boolean isTerminal()
    {
        if (cannibals==0 && missionaries==0 && boat=='r')return true;
        return false;
    }

    @Override
    public String toString() {
        String s="";
        String e="";
        String b="";
        for (int i = 0; i < missionaries; i++) {
            s += "M ";
        }
        for (int i = 0; i < cannibals; i++) {
            s += "C ";
        }
        for (int i = 0; i < missionariesRight; i++) {
            e += " M";
        }
        for (int i = 0; i < cannibalsRight; i++) {
            e += " C";
        }
        if (boat=='l') {b="<==>          ";}
        else {b="          <==>";}
        return s + "" + b + "" + e;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof State)) {
            return false;
        }
        State otherState = (State) obj;
        return missionaries == otherState.missionaries
                && cannibals == otherState.cannibals
                && missionariesRight == otherState.missionariesRight
                && cannibalsRight == otherState.cannibalsRight
                && boat == otherState.boat;
    }

    @Override
    public int compareTo(State s) {
        return Double.compare(this.cost, s.cost);
    }

}
