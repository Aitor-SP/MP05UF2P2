package ex3;

import ex2.HashTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertNull;

class HashTableTest {

    @ParameterizedTest
    @CsvSource({"2", "5", "20", "100"})
    void count(int n) {
        HashTable hashTable = new HashTable();
        //Con el bucle inserto n elementos y cuenta los que hay que antes no lo hacía
        for (int i = 0; i < n; i++) {
            hashTable.put("key"+i, "value"+i);
        }
        Assertions.assertEquals(n, hashTable.count());
        System.out.println(hashTable.toString());
        System.out.println("COUNT -> "+hashTable.count());
    }

    @ParameterizedTest
    @CsvSource({"2", "5", "20", "100"})
    void size(int n) {
        //Esto va bien, siempre ha de tener un tamaño de 16 por muchos puts que se hagan
        HashTable hashTable = new HashTable();
        //Con el bucle inserto n elementos y vemos como el SIZE ha de seguir siendo 16
        for (int i = 0; i < n; i++) {
            hashTable.put("key"+i, "value"+i);
        }
        Assertions.assertEquals(16, hashTable.size());
        System.out.println("SIZE -> "+hashTable.size());
    }

    @ParameterizedTest
    @CsvSource({"key, value", "key1, value1", "key2, value2"})
    void put(String key, String value) {
        HashTable hashTable = new HashTable();

        for (String keyTest : hashTable.getCollisionsForKey(key, 10)) {
            hashTable.put(keyTest, value);
            Assertions.assertEquals(value, hashTable.get(keyTest));
        }
        System.out.println(hashTable.toString());
    }

    @ParameterizedTest
    @CsvSource({"key, value", "key1, value1", "key2, value2"})
    void get(String key, String value) {
        ex1.HashTable hashTable = new ex1.HashTable();
        hashTable.put(key,value);
        hashTable.put("key","value");
        Assertions.assertEquals(value, hashTable.get(key));
        System.out.println(hashTable.toString());
        System.out.println("VALUE -> "+hashTable.get(key));
    }

    @ParameterizedTest
    @CsvSource({"key","10","21","32"})
    void drop(String key) {
        HashTable hashTable = new HashTable();
        //Aqui miro las key que puede colisionar con la key existente
        System.out.println(hashTable.getCollisionsForKey("key", 3));

        //Caso 2 -> key+value -> temp.next // Drop de la primera key y temp.next se mueve a la primera posición
        //Si key = 10 le añadimos el valor COLISIÓN
        if (key.equals("10")) {
            hashTable.put(key, "COLISIÓN" );
            //Añadimos una nueva key con un nuevo valor que será .next
            hashTable.put("key", "value");
            System.out.print("CASO 2");
            System.out.println(hashTable.toString());

            //Si eliminamos key, lo que era next ahora ocupará la primera posición
            hashTable.drop(key);
            System.out.println(hashTable.toString());
            Assertions.assertNotEquals(null, hashTable.toString());

            //Caso 1 -> Solo con una key+value, drop y dejamos la hashTable vacia
            // Si la key es igual a key O key1
        } else if (key.equals("key")) {
            System.out.print("CASO 1:");
            //Añadimos key y value
            hashTable.put("key", "value");
            System.out.println(hashTable.toString());
            //Dropeo la clave existente
            hashTable.drop(key);
            System.out.println("DESPUÉS DE DROP:");
            //Aquí ya está vacia la hashTable
            assertNull(hashTable.get(key));

            //Caso 3 -> 3 keys con sus values, eliminamos la que tiene prev y next. Next ahora ocupa esta posición y tiene .prev
        } else if (key.equals("key")){
            System.out.print("CASO 3");
            hashTable.put("key", "value");
            hashTable.put(key, "COLISIÓN2" );
            hashTable.put("10", "COLISIÓN3" );
            System.out.println(hashTable.toString());
            hashTable.drop(key);
            System.out.println(hashTable.toString());
            Assertions.assertNotEquals(null, hashTable.toString());
        } else if (key.equals(key)) {
                System.out.print("CASO 4");
                hashTable.put("key", "value");
                hashTable.put(key, "COLISIÓN2");
                hashTable.put("10", "COLISIÓN3");
                System.out.println(hashTable.toString());
                hashTable.drop("10");
                System.out.println(hashTable.toString());
                Assertions.assertNotEquals(null, hashTable.toString());
        }
    }
}