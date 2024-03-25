import java.util.*;
import java.io.*;

class TreeNode {
    int freq;
    char ch;
    TreeNode left;
    TreeNode right;

    public TreeNode(char ch, int freq) {
        this.ch = ch;
        this.freq = freq;
        left = null;
        right = null;
    }

    public TreeNode(TreeNode left, TreeNode right) {
        freq = left.freq + right.freq;
        ch = '\0';
        this.left = left;
        this.right = right;
    }
}

class Compressor {
    Map<Character, Integer> freq;
    PriorityQueue<TreeNode> pq;
    String text;
    TreeNode aNode, newNode;
    String compressedString;

    Map<Character, String> encodedMap;

    Compressor(String text) {
        try {
            freq = new HashMap<>();
            pq = new PriorityQueue<>((a, b) -> a.freq - b.freq);
            encodedMap = new HashMap<>();
            //
            // Class cls = FileOperationsTest.class;
            // InputStream inputStream = cls.getResourcesAsStream("./InputMessage.txt");
            // text = readFromInputStream(inputStream);
            this.text = text;
        } catch (Exception e) {
            System.out.println("Error occured in Compressor");
        }
    }

    void enMap() {
        for (int i = 0; i < text.length(); i++) {
            if (freq.containsKey(text.charAt(i))) {
                freq.put(text.charAt(i), freq.get(text.charAt(i)) + 1);
            } else {
                freq.put(text.charAt(i), 1);
            }
        }
    }

    TreeNode priority() {
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            char key = entry.getKey();
            int value = entry.getValue();
            aNode = new TreeNode(key, value);
            pq.add(aNode);
        }
        while (pq.size() > 1) {
            TreeNode first = pq.poll();
            TreeNode second = pq.poll();
            newNode = new TreeNode(first, second);
            pq.add(newNode);
        }
        return newNode;
    }

    void compress(TreeNode root, String temp) {
        if (root == null) {
            return;
        } else if (root.left == null && root.right == null) {
            encodedMap.put(root.ch, temp);
        }
        compress(root.left, temp + "0");
        compress(root.right, temp + "1");
    }

    String getBinCompressedString() {
        compressedString = "";
        for (int i = 0; i < text.length(); i++) {
            if (encodedMap.containsKey(text.charAt(i))) {
                compressedString += encodedMap.get(text.charAt(i));
            } else {
                System.out.println("Error");
            }
        }
        return compressedString;
    }

    void getCompressedString() {

    }
}

class Decompressor {
    String decompressedString;
    String text;
    TreeNode encodingRootNode;
    int i;

    Decompressor(String text, TreeNode encodingRootNode) {
        // opening file and copying the text into text variable
        this.text = text;
        this.encodingRootNode = encodingRootNode;
        decompressedString = "";
        i = 0;
    }

    String decompress(TreeNode root) {
        TreeNode curr = root;
        while (i < text.length()) {
            if (curr == null) {
                return null;
            } else if (curr.left == null && curr.right == null) {
                decompressedString += curr.ch;
                curr = root;
            } else if ((text.charAt(i)) == '0') {
                i++;
                curr = curr.left;
            } else if (text.charAt(i) == '1') {
                i++;
                curr = curr.right;
            }
        }
        if (curr != null && (curr.left == null && curr.right == null)) {

            if (curr == null) {
                return null;
            } else if (curr.left == null && curr.right == null) {
                decompressedString += curr.ch;
                curr = root;
            } else if ((text.charAt(i)) == '0') {
                i++;
                curr = curr.left;
            } else if (text.charAt(i) == '1') {
                i++;
                curr = curr.right;
            }
        }
        return decompressedString;
    }
}

class HuffmanEncoding {
    public static void main(String[] args) {
        Compressor c = new Compressor("Hello");
        c.enMap();
        TreeNode node = c.priority();
        c.compress(node, "");
        System.out.println(c.getBinCompressedString());

        Decompressor d = new Decompressor("1011000111", node);
        System.out.println(d.decompress(node));
    }
}
