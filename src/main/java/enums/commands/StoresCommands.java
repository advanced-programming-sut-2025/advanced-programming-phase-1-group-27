package enums.Commands;

public enum StoresCommands {
    ShowAllProducts("\\s*show\\s+all\\s+products\\s*"),
    ShowAllAvailableProducts("\\s*show\\s+all\\s+available\\s+products\\s*"),
    Purchase("\\s*purchhase\\s+(?<productName>.+)\\s+-n\\s+(?<count>.+)\\s*"),
    Sell("\\s*sell\\s+(?<productName).+)\\s+(?-n.+)\\s+count\\s*");

    private final String pattern;

    StoresCommands(String pattern) {
        this.pattern = pattern;
    }
}
