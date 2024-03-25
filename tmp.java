
import java.io.*;
import java.util.*;

import javax.swing.text.AttributeSet.CharacterAttribute;

import java.nio.file.Files;
import java.nio.file.Path;
import java.math.BigInteger;

import org.junit.Assert;
import static org.hamcrest.CoreMatchers.containsString;

import java.io.InputStream;
import java.lang.Class;

class Tmp {
    public static void main(String[] args) {
        String text;
        try {

            Class cls = FileOperationsTest.class;
            InputStream inputStream = cls.getResourcesAsStream("./InputMessage.txt");
            text = readFromInputStream(inputStream);
        } catch (Exception e) {
            System.out.println("Error occured in Compressor");
        }
    }
}
