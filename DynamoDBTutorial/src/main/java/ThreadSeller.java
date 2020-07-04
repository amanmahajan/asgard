import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = DynamoDBQueries.TABLE_NAME)
public class ThreadSeller {

  private String pk;
  private String sk;
  private String timeStamp;
  private String sellerId;
  private boolean archived;
  private boolean deleted;
  private String threadId;

  @DynamoDBHashKey
  public String getPk() {
    return pk;
  }


  public void setPk(String pk) {
    this.pk = pk;
  }

  @DynamoDBIndexRangeKey
  public String getSk() {
    return sk;
  }

  public void setSk(String sk) {
    this.sk = sk;
  }
  @DynamoDBAttribute
  public String getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }
  @DynamoDBAttribute
  public String getSellerId() {
    return sellerId;
  }

  public void setSellerId(String sellerId) {
    this.sellerId = sellerId;
  }
  @DynamoDBAttribute
  public boolean isArchived() {
    return archived;
  }

  public void setArchived(boolean archived) {
    this.archived = archived;
  }

  @DynamoDBAttribute
  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }
  @DynamoDBAttribute
  public String getThreadId() {
    return threadId;
  }

  public void setThreadId(String threadId) {
    this.threadId = threadId;
  }
}
