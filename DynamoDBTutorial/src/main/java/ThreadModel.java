import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = DynamoDBQueries.TABLE_NAME)
public class ThreadModel {
  String pk;
  String sk;
  ThreadBuyer threadBuyer;
  ThreadItem threadItem;
  ThreadSeller threadSeller;

  public ThreadBuyer getThreadBuyer() {
    return threadBuyer;
  }

  public void setThreadBuyer(ThreadBuyer threadBuyer) {
    this.threadBuyer = threadBuyer;
  }

  public ThreadItem getThreadItem() {
    return threadItem;
  }

  public void setThreadItem(ThreadItem threadItem) {
    this.threadItem = threadItem;
  }

  public ThreadSeller getThreadSeller() {
    return threadSeller;
  }

  public void setThreadSeller(ThreadSeller threadSeller) {
    this.threadSeller = threadSeller;
  }
  @DynamoDBHashKey
  public String getPk() {
    return pk;
  }

  public void setPk(String pk) {
    this.pk = pk;
  }
  @DynamoDBRangeKey
  public String getSk() {
    return sk;
  }

  public void setSk(String sk) {
    this.sk = sk;
  }
}
