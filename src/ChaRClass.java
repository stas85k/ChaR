/**
 * Created by stas on 20/02/2016.
 */
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ChaRClass {

    public static void main(String[] args) throws IOException {

        JFileChooser fc = new JFileChooser();
        int ret = fc.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            //Reading
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lnsL = new ArrayList<>();
            String ln;
            while ((ln = reader.readLine()) != null) {
                lnsL.add(ln);
            }
            reader.close();

            List<Character> chrAr = new ArrayList<>();
            String r = lnsL.toString();
            char[] str = r.toCharArray();
            for (int i = 0; i < str.length; i++) {
                if ((Character.UnicodeBlock.of(str[i]) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS))
                    chrAr.add(str[i]);
            }

            chrAr.sort(Comparator.<Character>naturalOrder());

            HashMap<Character, Integer> map = new HashMap<>();
            while (!chrAr.isEmpty()) {
                int k = 0;
                Character tm = null;
                for (int i = 0; i < chrAr.size(); i++) {
                    if (chrAr.get(0).equals(chrAr.get(i)))
                        k++;
                    tm = chrAr.get(0);
                }
                for (int j = 0; j < k; j++) {
                    chrAr.remove(tm);
                }
                map.put(tm, k);
            }

            List mapKeys = new ArrayList(map.keySet());
            List mapValues = new ArrayList(map.values());
            Collections.sort(mapValues);
            Collections.sort(mapKeys);

            LinkedHashMap sortedMap = new LinkedHashMap();

            Iterator valueIt = mapValues.iterator();
            while (valueIt.hasNext()) {
                Object val = valueIt.next();
                Iterator keyIt = mapKeys.iterator();

                while (keyIt.hasNext()) {
                    Object key = keyIt.next();
                    String comp1 = map.get(key).toString();
                    String comp2 = val.toString();

                    if (comp1.equals(comp2)) {
                        map.remove(key);
                        mapKeys.remove(key);
                        sortedMap.put(key, val);
                        break;
                    }
                }
            }
            Set s = sortedMap.entrySet();
            List<String> sLst = (List<String>) s.stream().map(Object::toString).collect(Collectors.toList());
            List<String> sLstM = new ArrayList<>();
            for (String sL : sLst) {
                sLstM.add(0, sL);
            }
            //sLstM.forEach(System.out::println);

            if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try (FileWriter fw = new FileWriter(fc.getSelectedFile())) {
                    for (String sLM : sLstM)
                        fw.write(sLM.replace("=", ".  ") + "." + "\n");
                    fw.flush();
                }
            }
        }
    }
}

