lithium-esb
===========

This is the integrations project used to consume HDFS logs from the events generated from the community system and then write those events as split events into message bus Kafka.
1) Read the messages
2) Perform decryption
3) Perform decompression
4) split messages based on \n
5) Write the messages in async to kafka
6) Also write a sample index fields to ElasticSearc so as to create a master list of events for co-relation and comparison.
7) Continuously poll for new files and update the status of the files in ES (Used as storage) for tracking and managing read operations.
