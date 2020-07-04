import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class DynamoDBQueries {

  private AmazonDynamoDB amazonDynamoDB;
  private DynamoDBMapper dynamoDBMapper;
  public static final String TABLE_NAME = "test_chat_discussion";

  public DynamoDBQueries(AmazonDynamoDB amazonDynamoDB) {

    this.amazonDynamoDB = amazonDynamoDB;
    this.dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
  }

  public void createNewThread() {
    SimpleDateFormat sdf;
    Date date = new Date(System.currentTimeMillis());
    sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateText = sdf.format(date);
    Map<String, AttributeValue> buyerThread = creatThreadBuyer();
    Map<String, AttributeValue> sellerThread = creatThreadSeller();
    Map<String, AttributeValue> itemThread = createThreadItem();
    buyerThread.put("last_update_timestamp", new AttributeValue(dateText));
    sellerThread.put("last_update_timestamp", new AttributeValue(dateText));
    itemThread.put("last_update_timestamp", new AttributeValue(dateText));
    Put createBuyerThread =
        new Put()
            .withTableName(TABLE_NAME)
            .withItem(buyerThread)
            .withConditionExpression("attribute_not_exists(" + "pk" + ")");
    Put createSellerThread =
        new Put()
            .withTableName(TABLE_NAME)
            .withItem(sellerThread)
            .withConditionExpression("attribute_not_exists(" + "pk" + ")");
    Put createItemThread =
        new Put()
            .withTableName(TABLE_NAME)
            .withItem(itemThread)
            .withConditionExpression("attribute_not_exists(" + "pk" + ")");

    Collection<TransactWriteItem> actions =
        Arrays.asList(
            new TransactWriteItem().withPut(createBuyerThread),
            new TransactWriteItem().withPut(createSellerThread),
            new TransactWriteItem().withPut(createItemThread));
    TransactWriteItemsRequest createOrderTransaction =
        new TransactWriteItemsRequest()
            .withTransactItems(actions)
            .withReturnConsumedCapacity(ReturnConsumedCapacity.TOTAL);
    try {
      amazonDynamoDB.transactWriteItems(createOrderTransaction);
      System.out.println("Transaction Successful");

    } catch (ResourceNotFoundException rnf) {
      System.err.println(
          "One of the table involved in the transaction is not found" + rnf.getMessage());
    } catch (InternalServerErrorException ise) {
      System.err.println("Internal Server Error" + ise.getMessage());
    } catch (TransactionCanceledException tce) {
      System.out.println("Transaction Cancelled");
    }
  }

  public ThreadModel get() {
    ThreadModel threadModel = new ThreadModel();
    threadModel.setPk("THREAD#456_123");
    DynamoDBQueryExpression<ThreadModel> queryExpression = new DynamoDBQueryExpression<ThreadModel>()
            .withHashKeyValues(threadModel);
    try{
      threadModel = dynamoDBMapper.load(ThreadModel.class, queryExpression);

    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return threadModel;

  }

  private Map<String, AttributeValue> creatThreadBuyer() {
    Map<String, AttributeValue> map = new HashMap<>();
    map.put("pk", new AttributeValue("THREAD#456_123"));
    map.put("sk", new AttributeValue("BUYER#123"));
    map.put("archived", new AttributeValue().withBOOL(false));
    map.put("deleted", new AttributeValue().withBOOL(false));
    map.put("thread_id", new AttributeValue("456_123"));
    map.put("buyer_id", new AttributeValue("123"));
    return map;
  }

  private Map<String, AttributeValue> creatThreadSeller() {
    Map<String, AttributeValue> map = new HashMap<>();
    map.put("pk", new AttributeValue("THREAD#456_123"));
    map.put("sk", new AttributeValue("SELLER#123"));
    map.put("archived", new AttributeValue().withBOOL(false));
    map.put("deleted", new AttributeValue().withBOOL(false));
    map.put("thread_id", new AttributeValue("456_123"));
    map.put("seller_id", new AttributeValue("789"));
    return map;
  }

  private Map<String, AttributeValue> createThreadItem() {
    Map<String, AttributeValue> map = new HashMap<>();
    map.put("pk", new AttributeValue("THREAD#456_123"));
    map.put("sk", new AttributeValue("ITEM#123"));
    map.put("archived", new AttributeValue().withBOOL(false));
    map.put("deleted", new AttributeValue().withBOOL(false));
    map.put("item_id", new AttributeValue("456"));
    map.put("thread_id", new AttributeValue("456_123"));
    return map;
  }
}
