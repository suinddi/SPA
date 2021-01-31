package com.memsys.web.vos;

public class DeleteUserVo {
    private String index;
    private int[] indexArray;
    private boolean isNormalized;

    public DeleteUserVo(String index) {
        try {
            this.index = index;
            String[] tempArray = this.index.split(",");
            indexArray = new int[tempArray.length];
            for (int i =0; i < indexArray.length; i++) {
                indexArray[i] = Integer.parseInt(tempArray[i]);
            }
            this.isNormalized = true;
        } catch (NumberFormatException numberFormatException) {
            this.isNormalized = false;
            this.index = null;
            this.indexArray = null;
        }
    }

    public String getIndex() {
        return index;
    }

    public int[] getIndexArray() {
        return indexArray;
    }

    public boolean isNormalized() {
        return isNormalized;
    }
}
