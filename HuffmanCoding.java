import java.util.*;
import java.lang.*;
import java.nio.charset.StandardCharsets;
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
    String binCompressedString;

    Map<Character, String> encodedMap;

    Compressor(String text) {
        try {
            freq = new HashMap<>();
            pq = new PriorityQueue<>((a, b) -> a.freq - b.freq);
            encodedMap = new HashMap<>();
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
        binCompressedString = "";
        for (int i = 0; i < text.length(); i++) {
            if (encodedMap.containsKey(text.charAt(i))) {
                binCompressedString += encodedMap.get(text.charAt(i));
            } else {
                System.out.println("Error");
            }
        }
        return binCompressedString;
    }

    int getOffset() {
        int offset = binCompressedString.length() % 8;
        return offset;
    }

    String getCompressedString(String binCompressedString) {

        List<Byte> byteArr = new ArrayList<>();
        int offset = getOffset();
        if (offset != 0) {

            int add = 8 - offset;
            for (int i = 0; i < add; i++) {
                binCompressedString += "0";
            }
        }
        for (int i = 0; i < binCompressedString.length() && i + 8 <= binCompressedString.length(); i += 8) {
            int j = i;
            byte num = 0;
            while (j < i + 8) {
                num *= 2;
                if (binCompressedString.charAt(j) == '1') {
                    num += 1;
                }
                j++;
            }
            // i = j;
            byteArr.add(num);
        }
        byte[] byteArray = new byte[byteArr.size()];
        for (int i = 0; i < byteArr.size(); i++) {
            byteArray[i] = byteArr.get(i);
        }
        compressedString = new String(byteArray, StandardCharsets.ISO_8859_1);
        return compressedString;
    }
}

class Decompressor {
    String decompressedString;
    String text;
    TreeNode encodingRootNode;
    int i;
    String compressedString;

    Decompressor(String compressedString, TreeNode encodingRootNode) {
        // opening file and copying the text into text variable
        this.compressedString = compressedString;
        this.encodingRootNode = encodingRootNode;
        decompressedString = "";
        text = "";
        i = 0;
    }

    String getBinDecompressedString(String compressedString, int offset) {

        String binaryString = new String();
        for (int i = 0; i < compressedString.length(); i++) {
            int j = (compressedString.charAt(i) + 256) % 256;
            String x = String.format("%8s", Integer.toBinaryString(j)).replace(' ', '0');
            binaryString += x;
        }
        text = binaryString.substring(0, binaryString.length() - (8 - offset));
        return text;
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

        String text = "";
        try {

            FileReader fr = new FileReader("./InputMessage.txt");

            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                text += line;
            }
            br.close();

        } catch (Exception e) {
            System.out.println("Exception occured during reading a file");
        }

        Compressor c = new Compressor(text);
        c.enMap();
        TreeNode node = c.priority();
        c.compress(node, "");
        String str1 = c.getCompressedString(c.getBinCompressedString());
        int offset = c.getOffset();

        Decompressor d = new Decompressor(str1, node);

        String str2 = d.getBinDecompressedString(str1, offset);
        try {

            FileWriter fw = new FileWriter("./OutputMessage.txt");
            fw.write(d.decompress(node));
            fw.close();
        } catch (Exception e) {
            System.out.println("Exception occured during writing a file");
        }
    }
}
