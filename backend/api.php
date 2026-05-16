<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET, POST");
header("Access-Control-Allow-Headers: Content-Type");
header("Content-Type: application/json; charset=UTF-8");

$host = 'localhost';
$db   = 'minesweeper_db';
$user = 'root';
$pass = '';
$charset = 'utf8mb4';

$dsn = "mysql:host=$host;dbname=$db;charset=$charset";
$options = [
    PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
    PDO::ATTR_EMULATE_PREPARES   => false,
];

try {
    $pdo = new PDO($dsn, $user, $pass, $options);
} catch (\PDOException $e) {
    http_response_code(500);
    echo json_encode(['error' => 'Connection failed: ' . $e->getMessage()]);
    exit;
}

$method = $_SERVER['REQUEST_METHOD'];

if ($method === 'GET') {
    // Get all matches
    $stmt = $pdo->query('SELECT * FROM matches ORDER BY date DESC');
    $matches = $stmt->fetchAll();
    echo json_encode($matches);
} elseif ($method === 'POST') {
    // Create new match
    $data = json_decode(file_get_contents('php://input'), true);

    if (isset($data['player_name']) && isset($data['score'])) {
        $playerName = $data['player_name'];
        $score = $data['score'];

        $stmt = $pdo->prepare('INSERT INTO matches (player_name, score) VALUES (?, ?)');
        
        try {
            $stmt->execute([$playerName, $score]);
            http_response_code(201);
            echo json_encode(['message' => 'Match saved successfully']);
        } catch (\PDOException $e) {
            http_response_code(500);
            echo json_encode(['error' => 'Database error: ' . $e->getMessage()]);
        }
    } else {
        http_response_code(400);
        echo json_encode(['error' => 'Invalid input']);
    }
} else {
    http_response_code(405);
    echo json_encode(['error' => 'Method not allowed']);
}
?>
