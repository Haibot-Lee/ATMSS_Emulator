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

$req = json_decode($_POST["BAMSReq"], false);

if (strcmp($req->msgType, "LoginReq") === 0) {

    //query
    $sql = "SELECT * FROM ATMcards WHERE cardNo='$req->cardNo' AND pin='$req->pin'";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
        $loginReply = $req->cardNo;
    } else {
        $loginReply = "Error";
    }
    //reply
    $reply->msgType = "LoginReply";
    $reply->cardNo = $req->cardNo;
    $reply->pin = $req->pin;
    $reply->cred = $loginReply;

} else if (strcmp($req->msgType, "GetAccReq") === 0) {
    $reply->msgType = "GetAccReply";
    $reply->cardNo = $req->cardNo;
    $reply->cred = $req->cred;
    $reply->accounts = "111-222-333/111-222-334/111-222-335/111-222-336";
} else if (strcmp($req->msgType, "WithdrawReq") === 0) {
    $reply->msgType = "WithdrawReply";
    $reply->cardNo = $req->cardNo;
    $reply->accNo = $req->accNo;
    $reply->cred = $req->cred;
    $reply->amount = $req->amount;
    $reply->outAmount = $req->amount;
} else if (strcmp($req->msgType, "DepositReq") === 0) {
    $reply->msgType = "DepositReply";
    $reply->cardNo = $req->cardNo;
    $reply->accNo = $req->accNo;
    $reply->cred = $req->cred;
    $reply->amount = $req->amount;
    $reply->depAmount = $req->amount;
} else if (strcmp($req->msgType, "EnquiryReq") === 0) {
    $reply->msgType = "EnquiryReply";
    $reply->cardNo = $req->cardNo;
    $reply->accNo = $req->accNo;
    $reply->cred = $req->cred;
    $reply->amount = "109700";
} else if (strcmp($req->msgType, "TransferReq") === 0) {
    $reply->msgType = "TransferReply";
    $reply->cardNo = $req->cardNo;
    $reply->cred = $req->cred;
    $reply->fromAcc = $req->fromAcc;
    $reply->toAcc = $req->toAcc;
    $reply->amount = $req->amount;
    $reply->transAmount = $req->amount;
}

echo json_encode($reply);
?>
