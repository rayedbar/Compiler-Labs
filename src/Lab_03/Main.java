package Lab_03;

public class Main {

    public static void main(String[] args) {
        String regex = "(a|b)*ab?b";
        String input = "ab";
        Scanner myScanner = new Scanner(regex);
        Parse parse = new Parse(myScanner);
        ParseNode tree = parse.creatTree();
        Nfa nfa = parse.treeToNfa(tree);
        nfa.show();
        nfa.showMatrix();
        Dfa dfa = new Dfa(nfa);
        dfa.subset_construct();
        dfa.print();
        dfa.verify(input);
    }

}
