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
    //reply
    $reply->msgType = "LoginReply";
    $reply->cardNo = $req->cardNo;
    $reply->pin = $req->pin;
    if ($result->num_rows > 0) {
        $reply->cred = $req->cardNo;
    } else {
        $reply->cred = "Error";
    }

} else if (strcmp($req->msgType, "GetAccReq") === 0) {

    //query
    $sql = "SELECT accNo FROM ATMcards WHERE cardNo='$req->cardNo'";
    $result = $conn->query($sql);
    $accounts = "";
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $accounts = $accounts . $row["accNo"] . "/";
        }
    }
    //reply
    $reply->msgType = "GetAccReply";
    $reply->cardNo = $req->cardNo;
    $reply->cred = $req->cred;
    $reply->accounts = $accounts;

} else if (strcmp($req->msgType, "WithdrawReq") === 0) {

    //query
    $sql = "SELECT balance FROM ATMcards WHERE cardNo='$req->cardNo' AND accNo='$req->accNo'";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $balance = $row["balance"];
        }
        if ($balance >= $req->amount) {
            $outAmount = $req->amount;
            //update balance
            $newBalance = $balance - $req->amount;
            $sql = "UPDATE ATMcards SET balance = $newBalance WHERE cardNo = '$req->cardNo' AND accNo = '$req->accNo'";
            $conn->query($sql);
        } else {
            $outAmount = -1;
        }
    } else {
        $outAmount = -1;
    }
    //reply
    $reply->msgType = "WithdrawReply";
    $reply->cardNo = $req->cardNo;
    $reply->accNo = $req->accNo;
    $reply->cred = $req->cred;
    $reply->amount = $req->amount;
    $reply->outAmount = $outAmount;

} else if (strcmp($req->msgType, "DepositReq") === 0) {

    //query
    $sql = "SELECT balance FROM ATMcards WHERE cardNo='$req->cardNo' AND accNo='$req->accNo'";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
        $depAmount = $req->amount;
        while ($row = $result->fetch_assoc()) {
            $balance = $row["balance"];
        }
        //update balance
        $newBalance = $balance + $req->amount;
        $sql = "UPDATE ATMcards SET balance = $newBalance WHERE cardNo = '$req->cardNo' AND accNo = '$req->accNo'";
        $conn->query($sql);
    } else {
        $depAmount = -1;
    }
    //reply
    $reply->msgType = "DepositReply";
    $reply->cardNo = $req->cardNo;
    $reply->accNo = $req->accNo;
    $reply->cred = $req->cred;
    $reply->amount = $req->amount;
    $reply->depAmount = $depAmount;

} else if (strcmp($req->msgType, "EnquiryReq") === 0) {

    //query
    $sql = "SELECT balance FROM ATMcards WHERE cardNo='$req->cardNo' AND accNo='$req->accNo'";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $balance = $row["balance"];
        }
    } else {
        $balance = -1;
    }
    //reply
    $reply->msgType = "EnquiryReply";
    $reply->cardNo = $req->cardNo;
    $reply->accNo = $req->accNo;
    $reply->cred = $req->cred;
    $reply->amount = $balance;

} else if (strcmp($req->msgType, "TransferReq") === 0) {

    //query
    $sql = "SELECT balance FROM ATMcards WHERE cardNo='$req->cardNo' AND accNo='$req->fromAcc'";
    $fromResult = $conn->query($sql);
    $sql = "SELECT balance FROM ATMcards WHERE cardNo='$req->cardNo' AND accNo='$req->toAcc'";
    $toResult = $conn->query($sql);
    if ($fromResult->num_rows > 0 && $toResult->num_rows > 0) {
        while ($row = $fromResult->fetch_assoc()) {
            $fromBalance = $row["balance"];
        }
        while ($row = $toResult->fetch_assoc()) {
            $toBalance = $row["balance"];
        }
        if ($fromBalance >= $req->amount) {
            $transAmount = $req->amount;
            //update balance
            $newFromBalance = $fromBalance - $req->amount;
            $sql = "UPDATE ATMcards SET balance = $newFromBalance WHERE cardNo = '$req->cardNo' AND accNo = '$req->fromAcc'";
            $conn->query($sql);
            $newToBalance = $toBalance + $req->amount;
            $sql = "UPDATE ATMcards SET balance = $newToBalance WHERE cardNo = '$req->cardNo' AND accNo = '$req->toAcc'";
            $conn->query($sql);
        } else {
            $transAmount = -1;
        }

    } else {
        $transAmount = -1;
    }
    //reply
    $reply->msgType = "TransferReply";
    $reply->cardNo = $req->cardNo;
    $reply->cred = $req->cred;
    $reply->fromAcc = $req->fromAcc;
    $reply->toAcc = $req->toAcc;
    $reply->amount = $req->amount;
    $reply->transAmount = $transAmount;

}

echo json_encode($reply);
?>
