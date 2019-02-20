# Kafka File Listener
This project consist of three unique applications.<br />
<h2>Data Generator</h2>
<p>Generates log like data. These data consist of six columns; timestamp, log level, data, IP, host, location.</p> 
<p><b>Timestamp: </b> gets from currentTimeStamp.</p>
<p><b>Log Level: </b> gets from a list. Default: debug, info, warn, error, fatal, trace</p>
<p><b>Data: </b> gets from randomly created string. There are two parameters for the length in DataGeneratorMain.java. Data length will be between minRandomDataLength and maxRandomDataLength </p>
<p><b>IP: </b> gets from randomly created numbers. 0.0.0.0 to 255.255.255.255</p>
<p><b>Host: </b> gets from randomly created string and .com</p>
<p><b>Location: </b> gets from a list. Default: Istanbul, Ankara...</p>
<p>The generator runs on different thread and contains some wait() function to simulate as real log. You can change this simulation durations from simulatorMinWaitTime and simulatorMaxWaitTime parameters in DataGeneratorMain.java </p>
<p>All log files has a size limitation, by default 5KB. When a log file size reaches it, application continues with next file.</p>
<p>Log file name's syntax is log_NUMBER.log. NUMBER starts with 0 and increments on each size limit.</p>

<h2>Data Reader</h2>
<p>Listens a directory and if any file changes / updates / deletes, triggers a function.</p>
<p><b>On File Create:</b> Creates a new File Change Listener for this file.</p>
<p><b>On File Change:</b> Uploads changes to kafka topic.</p>
<p><b>On File Delete:</b> Stops File Change Listener for the file.</p>

<h2>Data Processor</h2>
<p>Reads data from a kafka topic and sends / stores them to solr database.</p>
