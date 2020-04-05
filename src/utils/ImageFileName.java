package utils;

public enum ImageFileName {
    WBISHOP_PNG("wbishop.png"),
    BBISHOP_PNG("bbishop.png"),
    WKNIGHT_PNG("wknight.png"),
    BKNIGHT_PNG("bknight.png"),
    WROOK_PNG("wrook.png"),
    BROOK_PNG("brook.png"),
    WKING_PNG("wking.png"),
    BKING_PNG("bking.png"),
    BQUEEN_PNG("bqueen.png"),
    WQUEEN_PNG("wqueen.png"),
    WPAWN_PNG("wpawn.png"),
    BPAWN_PNG("bpawn.png");

    private String fileName;

    ImageFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getFileName() {
        return fileName;
    }
}
