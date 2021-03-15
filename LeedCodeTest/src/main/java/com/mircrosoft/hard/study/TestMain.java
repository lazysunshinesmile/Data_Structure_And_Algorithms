package com.mircrosoft.hard.study;


import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.*;

public class TestMain {
    public static void main(String[] args) {
        char[] data = {0x7, 0x4b, 0x37, 0x3, 0x38, 0x16, 0, 0, 0x34, 0xff, 0x69, 0x22, 0, 0x44, 0, 0x15, 0, 0x3f, 0x1,
                0x50, 0, 0xa8, 0x23, 0x1, 0x1, 0x1, 0, 0, 0, 0x1,
                0, 0x1, 0x1, 0, 0, 0x1, 0, 0x1, 0, 0x1,
                0, 0x1, 0, 0, 0, 0, 0, 0, 0x1, 0,
                0x1, 0x1, 0x1, 0x1, 0x1,
                0x1, 0x11, 0x11, 0x11, 0x11,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0};

        String right = String.valueOf(data);
//        Solution solution = new Solution();
//        System.out.println(solution.countRestrictedPaths(6, new int[][]
//        { {6,2,9},{2,1,7},{6,5,10},{4,3,1},{3,1,8},{4,6,4},{5,1,7},{1,4,7},{4,5,3},{3,6,6},{5,3,9},
//        {1,6,6},{3,2,2},{5,2,8}}));
//        System.out.println(solution.countRestrictedPaths(7, new int[][]
//                {{1,3,1},{4,1,2},{7,3,4},{2,5,3},{5,6,1},{6,7,2},{7,5,3},{2,6,4}}));
//        byte[] datas = HexEncoding.decode(dataText.toCharArray());
//        String dataText = "033716000034ff692200"+
//                "440015003f015000a823"+
//                "01010100000001000101"+
//                "00000100010001000100"+
//                "000000000001000101010101"+
//                "01111111110000000000"+
//                "000000000000000000000000";
//        dataText = "074b37033816000034ff692200440015003f015000a823010101000000010001010000010001000100010000000000000100010101010101111111110000000000000000000000000000000000";
//        try {
//            byte[] datas = decode(dataText.toCharArray());
//            char[] s = encode(datas);
//            String ss = String.valueOf(s);
//            System.out.println( ss);
//            System.out.println(dataText);
//            System.out.println(  "sendMsg: "+ ss.equalsIgnoreCase(dataText));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        System.out.println("right chars:" + Arrays.toString(data));
        System.out.println("rigth string:" + right);
        String s= "074b37033816000034ff692200440015003f015000a82301010100" +
                "00000100010100000100010001000100000000000001000101010101011111" +
                "11110000000000000000000000000000000000";
        byte[] bs = decode(s.toCharArray());
        char[] af = getChars(bs);
        System.out.println("right chars:"+Arrays.toString(af));


        System.out.println(right.equalsIgnoreCase(String.valueOf(af)));

        System.out.println(right.length());
//        for(int i=0; i<right.length() && i< af.length; ) {
//
//        }
    }

    public static byte[] decode(char[] encoded) throws IllegalArgumentException {
        return decode(encoded, false);
    }

    private static char[] getChars(byte[] bytes) {
        char[] ret = new char[bytes.length];
        for(int i=0; i<bytes.length; i++) {
            if(bytes[i] < 0) {
                ret[i] =(char) (256 + (int) (bytes[i]));
            } else {
                ret[i] = (char) bytes[i];
            }
        }

        return ret;
    }

    /**
     * Decodes the provided hexadecimal string into a byte array. If {@code allowSingleChar}
     * is {@code true} odd-length inputs are allowed and the first character is interpreted
     * as the lower bits of the first result byte.
     *
     * Throws an {@code IllegalArgumentException} if the input is malformed.
     */
    public static byte[] decode(char[] encoded, boolean allowSingleChar) throws IllegalArgumentException {
        int resultLengthBytes = (encoded.length + 1) / 2;
        byte[] result = new byte[resultLengthBytes];

        int resultOffset = 0;
        int i = 0;
        if (allowSingleChar) {
            if ((encoded.length % 2) != 0) {
                // Odd number of digits -- the first digit is the lower 4 bits of the first result byte.
                result[resultOffset++] = (byte) toDigit(encoded, i);
                i++;
            }
        } else {
            if ((encoded.length % 2) != 0) {
                throw new IllegalArgumentException("Invalid input length: " + encoded.length);
            }
        }

        for (int len = encoded.length; i < len; i += 2) {
            result[resultOffset++] = (byte) ((toDigit(encoded, i) << 4) | toDigit(encoded, i + 1));
        }

        return result;
    }


    private static int toDigit(char[] str, int offset) throws IllegalArgumentException {
        // NOTE: that this isn't really a code point in the traditional sense, since we're
        // just rejecting surrogate pairs outright.
        int pseudoCodePoint = str[offset];

        if ('0' <= pseudoCodePoint && pseudoCodePoint <= '9') {
            return pseudoCodePoint - '0';
        } else if ('a' <= pseudoCodePoint && pseudoCodePoint <= 'f') {
            return 10 + (pseudoCodePoint - 'a');
        } else if ('A' <= pseudoCodePoint && pseudoCodePoint <= 'F') {
            return 10 + (pseudoCodePoint - 'A');
        }

        throw new IllegalArgumentException("Illegal char: " + str[offset] +
                " at offset " + offset);
    }
    private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();


    public static char[] encode(byte[] data) {
        return encode(data, 0, data.length);
    }

    /**
     * Encodes the provided data as a sequence of hexadecimal characters.
     */
    public static char[] encode(byte[] data, int offset, int len) {
        char[] result = new char[len * 2];
        for (int i = 0; i < len; i++) {
            byte b = data[offset + i];
            int resultIndex = 2 * i;
            result[resultIndex] = (HEX_DIGITS[(b >>> 4) & 0x0f]);
            result[resultIndex + 1] = (HEX_DIGITS[b & 0x0f]);
        }

        return result;
    }



    private static void print(int[][] ret) {
        for (int i=0; i<ret.length; i++) {
            System.out.println(Arrays.toString(ret[i]));
        }
    }

    public static class MinSegmentTree {
        private ArrayList<Integer> minSegmentTree;
        private int n;

        public MinSegmentTree(int[] arr) {
            n = arr.length;
            minSegmentTree = new ArrayList<>(2 * n);

            for  (int i = 0; i < n; i++) {
                minSegmentTree.add(0);
            }

            for (int i = 0; i < n; i++) {
                minSegmentTree.add(arr[i]);
            }

            for (int i = n - 1; i > 0; i--) {
                minSegmentTree.set(i, Math.min(minSegmentTree.get(2 * i), minSegmentTree.get(2 * i + 1)));
            }
        }

        public void update(int i, int value) {
            i += n;
            minSegmentTree.set(i,  value);

            while (i > 1) {
                i /= 2;
                minSegmentTree.set(i, Math.min(minSegmentTree.get(2 * i), minSegmentTree.get(2 * i + 1)));
            }
        }


        public int minimum(int left, int right) {
            left += n;
            right += n;
            int min = Integer.MAX_VALUE;

            while (left < right) {
                if ((left & 1) == 1) {
                    min = Math.min(min, minSegmentTree.get(left));
                    left++;
                }

                if ((right & 1) == 1) {
                    min = Math.min(min,  minSegmentTree.get(right));
                    right--;
                }
                left >>= 1;
                right >>= 1;
            }

            return min;
        }
    }

    static class ThroneInheritance {
        static  class Person {
            public Person(String name, int identiy) {
                this.name = name;
                this.identiy = identiy;
            }

            public int identiy = 0; //0国王 1 是儿子 2 是孙子
            public boolean isDeath = false;
            public String  name;
            public Person next;


            public boolean equals(Person p, int identiy) {
                return this.name.equals(p.name);
            }

            public String toString() {
                return "name:" + name + ", identiy:" + identiy + ", isDeath:" + isDeath
                        + ", next:" + next.name;
            }
        }

        HashMap<String, Person> mAlls = new HashMap<>();

        Person mKing;
        public ThroneInheritance(String kingName) {
            mKing = new Person(kingName, 0);
            mAlls.put(kingName, mKing);
        }

        public void birth(String parentName, String childName) {
            Person parent = mAlls.get(parentName);
            Person child = new Person(childName, parent.identiy-1);
            mAlls.put(childName, child);
//            if(parent.next == null) {
//                parent.next = child;
//            } else if(parent.next.identiy >= parent.identiy) {
//                child.next = parent.next;
//                parent.next = child;
//            } else if(parent.identiy > parent.next.identiy) {
//                while(parent != null && parent.next != null && parent.next.identiy <= parent.identiy) {
//                    parent = parent.next;
//                }
//
//                if(parent.next == null) {
//                    parent.next = child;
//                } else {
//                    child.next = parent.next;
//                    parent.next = child;
//                }
//            }
            /*if(parent.identiy == 0) {
                while(parent.next != null ) {
                    parent = parent.next;
                }
                parent.next = child;
            } else if(parent.identiy == -1)*/
            int parentIdnetiy = parent.identiy;
                while (parent.next != null && parent.next.identiy < parentIdnetiy) {
                    parent = parent.next;
                }
                child.next = parent.next;
                parent.next = child;
        }
//[null,null,null,null,null,null,
//        ["king","logan","leonard","hosea","carl","ronda"],null,null,null,["king","logan","hosea","carl","ronda","betty","helen"],null,null,["king","logan","hosea","alfred","carl","ronda","helen"],["king","logan","hosea","alfred","carl","ronda","helen"]]
        public void death(String name) {
            mAlls.get(name).isDeath = true;
        }

        public List<String> getInheritanceOrder() {
            Person p = mKing;
            List<String> ret = new LinkedList<>();
            while(p != null) {
                if(!p.isDeath) {
                    ret.add(p.name);
                }
                p = p.next;
            }
            return ret;
        }
    }
}
