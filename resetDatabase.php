<?php
$servername = "cslinux0.comp.hkbu.edu.hk";
$username = "comp4107_grp01";
$password = "439811";
$dbname = "comp4107_grp01";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "DROP TABLE ATMcards";
$conn->query($sql);
$sql = "CREATE TABLE ATMcards(
    cardNo    CHAR(8),
    pin       CHAR(6) NOT NULL,
    accNo CHAR(10),
    balance   REAL DEFAULT 0,
    primary key (cardNo, accNo)
)";
$conn->query($sql);
$sql = "INSERT INTO ATMcards VALUES ('41070001', '000001', '41070001-0', 1000);";
$conn->query($sql);
$sql = "INSERT INTO ATMcards VALUES ('41070001', '000001', '41070001-1', 1100);";
$conn->query($sql);
$sql = "INSERT INTO ATMcards VALUES ('41070001', '000001', '41070001-2', 1200);";
$conn->query($sql);
$sql = "INSERT INTO ATMcards VALUES ('41070001', '000001', '41070001-3', 1300);";
$conn->query($sql);
$sql = "INSERT INTO ATMcards VALUES ('41070002', '000002', '41070002-0', 2000);";
$conn->query($sql);
$sql = "INSERT INTO ATMcards VALUES ('41070002', '000002', '41070002-1', 2100);";
$conn->query($sql);
$sql = "INSERT INTO ATMcards VALUES ('41070003', '000003', '41070003-0', 3000);";
$conn->query($sql);

$sql = "SELECT * FROM ATMcards;";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    echo "cardNo   pin    accNo      balance\n";
    while ($row = $result->fetch_assoc()) {
        echo $row["cardNo"] . " " . $row["pin"] . " " . $row["accNo"] . " " . $row["balance"] . "\n";
    }
} else {
    echo "0 results";
}

$conn->close();

?>
