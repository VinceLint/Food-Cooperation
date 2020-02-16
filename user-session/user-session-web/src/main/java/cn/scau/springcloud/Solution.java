package cn.scau.springcloud;

public class Solution {
    public boolean isSubsequence(String s, String t) {
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        int index = 0;
        for (int i = 0; i < tChars.length; i++) {
            if (index >= sChars.length) {
                break;
            }
            if (sChars[index] == tChars[i]) {
                index++;
            }
        }
        if (index >= sChars.length) return true;
        return false;
    }
}
