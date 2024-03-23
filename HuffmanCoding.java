import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class HuffmanNode implements Comparable<HuffmanNode> {
    char data;
    int frequency;
    HuffmanNode left, right;

    public HuffmanNode(char data, int frequency) {
        this.data = data;
        this.frequency = frequency;
        this.left = this.right = null;
    }

    public int compareTo(HuffmanNode node) {
        return this.frequency - node.frequency;
    }

    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }
}

class HuffmanTreeBuilder {
    public static HuffmanNode buildHuffmanTree(Map<Character, Integer> frequencies) {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();

            HuffmanNode newNode = new HuffmanNode('\0', left.frequency + right.frequency);
            newNode.left = left;
            newNode.right = right;

            priorityQueue.add(newNode);
        }

        return priorityQueue.poll();
    }
}

class HuffmanEncoder {
    private Map<Character, String> huffmanCodes = new HashMap<>();

    public HuffmanEncoder(HuffmanNode root) {
        buildHuffmanCodes(root, "");
    }

    private void buildHuffmanCodes(HuffmanNode root, String code) {
        if (root == null) {
            return;
        }

        if (root.isLeaf()) {
            huffmanCodes.put(root.data, code);
        }

        buildHuffmanCodes(root.left, code + "0");
        buildHuffmanCodes(root.right, code + "1");
    }

    public String compress(String text) {
        StringBuilder compressedText = new StringBuilder();

        for (char c : text.toCharArray()) {
            compressedText.append(huffmanCodes.get(c));
        }

        return compressedText.toString();
    }
}

public class HuffmanCoding {

    public static void main(String[] args) {
        String inputFileName = "metin.txt";
        String outputFileName = "sikistirilmis_metin.txt";

        try {
            
            String text = readFromFile(inputFileName);

            Map<Character, Integer> frequencies = calculateFrequencies(text);
            
            HuffmanNode root = HuffmanTreeBuilder.buildHuffmanTree(frequencies);
         
            HuffmanEncoder encoder = new HuffmanEncoder(root);
        
            String compressedText = encoder.compress(text);
           
            writeToFile(outputFileName, compressedText);
  
            String compressedTextFromFile = readFromFile(outputFileName);
         
            String decompressedText = decompress(compressedTextFromFile, root);
         
            System.out.println("Orijinal Metin:\n" + text);
            System.out.println("\nSıkıştırılmış Metin:\n" + compressedText);
            System.out.println("\nÇözülmüş Metin:\n" + decompressedText);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFromFile(String fileName) throws IOException {
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int c;
            while ((c = reader.read()) != -1) {
                text.append((char) c);
            }
        }
        return text.toString();
    }

    private static void writeToFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }

    private static Map<Character, Integer> calculateFrequencies(String text) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        return frequencies;
    }

    private static String decompress(String compressedText, HuffmanNode root) {
        StringBuilder decompressedText = new StringBuilder();
        HuffmanNode current = root;

        for (char bit : compressedText.toCharArray()) {
            if (bit == '0') {
                current = current.left;
            } else {
                current = current.right;
            }

            if (current.isLeaf()) {
                decompressedText.append(current.data);
                current = root;
            }
        }

        return decompressedText.toString();
    }
}
