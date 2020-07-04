import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class DynamoDBManager {

  private DynamoDB dycnamoDB;
  private AmazonDynamoDB amazonDynamoDB;

  private static final String AMAZON_ACCESS_KEY = "DELETED";
  private static final String AMAZON_SECRET_KEY = "DELETED";

  public DynamoDBManager() {

    AWSCredentials credentials = new BasicAWSCredentials(AMAZON_ACCESS_KEY, AMAZON_SECRET_KEY);
    amazonDynamoDB = new AmazonDynamoDBClient(credentials);
  }

  public AmazonDynamoDB getAmazonDynamoDB() {
    return amazonDynamoDB;
  }

  public static void main(String[] args) {
    DynamoDBManager dynamoDBManager = new DynamoDBManager();

    AmazonDynamoDB ddb =
        AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.US_EAST_2)
            .withCredentials(
                new AWSStaticCredentialsProvider(
                    new BasicAWSCredentials(AMAZON_ACCESS_KEY, AMAZON_SECRET_KEY)))
            .build();
    DynamoDBQueries dynamoDBQueries = new DynamoDBQueries(ddb);

    // System.out.println(dynamoDBManager.getDynamoDB().getTable("test_chat_discussion"));
    // dynamoDBQueries.createNewThread();
    dynamoDBQueries.get();
  }
}
