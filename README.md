# Huffman-Coding

**HUFFMAN KODLAMASI?**

Bu proje, Huffman kodlaması kullanarak metin sıkıştırma işlemi gerçekleştiren bir Java uygulamasını içerir.


**HUFFMAN KODLAMASI NEDİR?**

Huffman kodlaması, bilgiyi daha az yer kaplayacak şekilde sıkıştırmak için kullanılan bir yöntemdir. Bu yöntemde, sık kullanılan karakterlere daha kısa kodlar atanarak daha az bit kullanılır.


**NASIL KULLANILIR?**

*1-Metin Sıkıştırma:*
HuffmanCoding sınıfı içinde bulunan main metodu çalıştırılarak bir metin dosyası sıkıştırılabilir.
metin.txt dosyasına sıkıştırılacak metin yazılır.
Program çalıştırıldığında, metin sıkıştırılır ve sikistirilmis_metin.txt dosyasına kaydedilir.


*2-Metin Çözme:*
Sıkıştırılmış metin, sikistirilmis_metin.txt dosyasından okunarak çözülebilir.
HuffmanCoding sınıfında bulunan decompress metodu kullanılarak sıkıştırılmış metin çözülür.


**ÖRNEK KULLANIM:**


```java
public class Main {
    public static void main(String[] args) {
        String inputFileName = "metin.txt";
        String outputFileName = "sikistirilmis_metin.txt";

        try {
            // Metni oku
            String text = readFromFile(inputFileName);

            // Frekansları hesapla
            Map<Character, Integer> frequencies = calculateFrequencies(text);

            // Huffman ağacı oluştur
            HuffmanNode root = HuffmanTreeBuilder.buildHuffmanTree(frequencies);

            // Kodlayıcı oluştur
            HuffmanEncoder encoder = new HuffmanEncoder(root);

            // Metni sıkıştır
            String compressedText = encoder.compress(text);

            // Sıkıştırılmış metni dosyaya yaz
            writeToFile(outputFileName, compressedText);

            // Sıkıştırılmış metni dosyadan oku
            String compressedTextFromFile = readFromFile(outputFileName);

            // Metni çöz
            String decompressedText = decompress(compressedTextFromFile, root);

            // Sonuçları yazdır
            System.out.println("Orijinal Metin:\n" + text);
            System.out.println("\nSıkıştırılmış Metin:\n" + compressedText);
            System.out.println("\nÇözülmüş Metin:\n" + decompressedText);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
}

