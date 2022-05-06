public class Block {
    private String hash;
    private String previousHash;
    private String data;
    private long timeStamp;
    private int nonce;
    
    File file = new File ("c:\\test\\temp\\BlockChin.txt");
    FileWriter f1 = new FileWriter(file);
 
    public Block(String data, String previousHash, long timeStamp) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.hash = calculateBlockHash();
    }
    public String calculateBlockHash() {
    String dataToHash = previousHash 
      + Long.toString(timeStamp) 
      + Integer.toString(nonce) 
      + data;
    MessageDigest digest = null;
    byte[] bytes = null;
    try {
        digest = MessageDigest.getInstance("SHA-256");
        bytes = digest.digest(dataToHash.getBytes(UTF_8));
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
        logger.log(Level.SEVERE, ex.getMessage());
    }
    StringBuffer buffer = new StringBuffer();
    for (byte b : bytes) {
        buffer.append(String.format("%02x", b));
    }
    f1.write($"{buffer.toString()}\n");
    f1.close();

    return buffer.toString();
}
public String mineBlock(int prefix) {
    String prefixString = new String(new char[prefix]).replace('\0', '0');
    while (!hash.substring(0, prefix).equals(prefixString)) {
        nonce++;
        hash = calculateBlockHash();
    }
    f1.write($"{hash}\n");
    f1.close();
    return hash;
}
    
List<Block> blockchain = new ArrayList<>();
int prefix = 4;
String prefixString = new String(new char[prefix]).replace('\0', '0');
@Test
public void givenBlockchain_whenNewBlockAdded_thenSuccess() {
    Block newBlock = new Block(
      "The is a New Block.", 
      blockchain.get(blockchain.size() - 1).getHash(),
      new Date().getTime());
    newBlock.mineBlock(prefix);
    assertTrue(newBlock.getHash().substring(0, prefix).equals(prefixString));
    blockchain.add(newBlock);
}
@Test
public void givenBlockchain_whenValidated_thenSuccess() {
    boolean flag = true;
    for (int i = 0; i < blockchain.size(); i++) {
        String previousHash = i==0 ? "0" : blockchain.get(i - 1).getHash();
        flag = blockchain.get(i).getHash().equals(blockchain.get(i).calculateBlockHash())
          && previousHash.equals(blockchain.get(i).getPreviousHash())
          && blockchain.get(i).getHash().substring(0, prefix).equals(prefixString);
            if (!flag) break;
    }
    assertTrue(flag);
}
}
