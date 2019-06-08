Examples in this package are based on following GitHub repo:

https://github.com/awsdocs/aws-doc-sdk-examples/tree/master/javav2/example_code/dynamodb/src/main/java/com/example/dynamodb

This is linked from https://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/examples-dynamodb.html, and is the only
(limited) documentation for the v2 java dynamodb API

Also see the dynamodb examples linked from https://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/welcome.html,
and https://docs.aws.amazon.com/sdk-for-java/index.html#aws-sdk-for-java-version-2.x

Older (v1) documentation is at:

https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/JavaDocumentAPIItemCRUD.html
https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GettingStarted.Java.html

To start local database:

Note: (based on https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html)

cd ~/code/third-party/dynamodb
java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb