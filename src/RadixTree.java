import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;

public class RadixTree<V> {
    private static final int NO_MISMATCH = -1;

    private static int getFirstMismatchLetter(String word, String label) {
        int LENGTH = Math.min(word.length(), label.length());
        for (int i = 0; i < LENGTH; i++) {
            if (word.charAt(i) != label.charAt(i))
                return i;
        }

        return NO_MISMATCH;
    }

    private class Node {
        private final HashMap<Character, Node> next;
        private Node parent;
        private V value;
        private String label;

        public Node(V value, String label) {
            this.value = value;
            this.label = label;
            this.parent = null;
            next = new HashMap<>();
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            if (this.value == null)
                this.value = value;
            else System.out.println("This key has already exist");
        }

        public void clearValue() {
            this.value = null;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public Node getParent() {
            return parent;
        }

        public String getLabel() {
            return label;
        }

        public Node getNextNode(Character transitionChar) {
            return next.get(transitionChar);
        }

        public void addNode(String label, V value) {
            Node addedNode = new Node(value, label);
            this.connectNode(label.charAt(0), addedNode);
        }

        public void connectNode(Character transitionCHar, Node addedNode) {
            next.put(transitionCHar, addedNode);
            addedNode.setParent(this);
        }

        public void cutNode(Character transitionCHar, Node cuttedNode) {
            next.remove(transitionCHar, cuttedNode);
            cuttedNode.setParent(null);
        }

        public Node splitNode(int splitPosition, V newValue) {
            Node newNode = new Node(newValue, this.label.substring(0, splitPosition));

            this.label = this.label.substring(splitPosition);
            newNode.connectNode(this.label.charAt(0), this);

            return newNode;
        }

        public Node mergeParentNode() {
            Node currParent = this.getParent();
            this.label = currParent.getLabel().concat(this.label);
            return this;
        }

        public int countNextNodes() {
            return next.size();
        }

        public Collection<Node> getAllNextNode() {
            return next.values();
        }
    }

    private final Node root;

    RadixTree() {
        root = new Node(null, null);
    }

    void insert(String word, V value) {
        word = '$' + word;
        Node current = root;
        int wordIndex = 0;
        while (wordIndex < word.length()) {
            Character transitionChar = word.charAt(wordIndex);
            Node nextNode = current.getNextNode(transitionChar);
            String currentString = word.substring(wordIndex);

            if (nextNode == null) {
                current.addNode(currentString, value);
                break;
            }

            int splitPosition = getFirstMismatchLetter(currentString, nextNode.getLabel());
            if (splitPosition == NO_MISMATCH) {
                if (currentString.length() == nextNode.getLabel().length()) {
                    nextNode.setValue(value);
                    break;
                } else if (currentString.length() < nextNode.getLabel().length()) {
                    nextNode = nextNode.splitNode(currentString.length(), value);
                    current.connectNode(currentString.charAt(0), nextNode);
                    break;
                } else {
                    splitPosition = nextNode.getLabel().length();
                }
            } else {
                nextNode = nextNode.splitNode(splitPosition, null);
                current.connectNode(currentString.charAt(0), nextNode);
            }

            wordIndex += splitPosition;
            current = nextNode;
        }
    }

    private static class Pair<K, V2> {
        private final K key;
        private final V2 value;

        Pair(K key, V2 value) {
            this.key = key;
            this.value = value;
        }
    }


    private Pair<Node, String> prefixMatches(String prefix) {
        prefix = '$' + prefix;
        Node current = root;
        int wordIndex = 0;
        while (wordIndex < prefix.length()) {
            Character transitionChar = prefix.charAt(wordIndex);
            Node nextNode = current.getNextNode(transitionChar);
            if (nextNode == null) {
                return new Pair<>(null, null);
            }

            String currentString = prefix.substring(wordIndex);
            int mismatchLetter = getFirstMismatchLetter(currentString, nextNode.getLabel());
            if (mismatchLetter == NO_MISMATCH) {
                if (currentString.length() <= nextNode.getLabel().length()) {
                    return new Pair<>(nextNode, currentString);
                } else {
                    mismatchLetter = nextNode.getLabel().length();
                }
            } else {
                return new Pair<>(null, null);
            }

            wordIndex += mismatchLetter;
            current = nextNode;
        }

        return new Pair<>(null, null);
    }

    private ArrayList<V> getWordsAfter(Node node) {
        if (node == null) {
            return new ArrayList<>();
        }
        ArrayList<V> resultArr = new ArrayList<>();
        Stack<Node> stateStack = new Stack<>();

        stateStack.add(node);
        while (!stateStack.empty()) {
            Node currNode = stateStack.pop();

            if (currNode.getValue() != null)
                resultArr.add(currNode.getValue());

            Collection<Node> nextNodes = currNode.getAllNextNode();
            for (Node nextNode : nextNodes) {
                stateStack.push(nextNode);
            }
        }

        return resultArr;
    }

    public V search(String word) {
        Pair<Node, String> matches = prefixMatches(word);
        if (matches.key == null) {
            return null;
        } else {
            if (matches.key.getLabel().length() == matches.value.length())
                return matches.key.value;
            else
                return null;
        }
    }

    public ArrayList<V> prefixSearch(String prefix) {
        Pair<Node, String> matches = prefixMatches(prefix);
        return getWordsAfter(matches.key);
    }

    public void delete(String word) {
        Pair<Node, String> matches = prefixMatches(word);
        if (matches.key != null) {
            if (matches.key.getLabel().length() == matches.value.length()) {
                Node current = matches.key;
                current.clearValue();
                while (current.getParent() != null) {
                    Node parent = current.getParent();
                    if (current.countNextNodes() == 0) {
                        parent.cutNode(current.getLabel().charAt(0), current);
                    } else if (current.countNextNodes() == 1 && current.getValue() == null) {
                        Node child = current.getAllNextNode().iterator().next();
                        parent.connectNode(current.getLabel().charAt(0), child.mergeParentNode());
                    }
                    current = parent;
                }
            }
        }
    }
}
