const canvas = document.getElementById("gameCanvas");
const ctx = canvas.getContext("2d");
const gameOverText = document.getElementById("gameOverText");
const menu = document.getElementById("menu");

const blockSize = 20; // Size of each block (snake and food)
let snake = [
  { x: 160, y: 200 },
  { x: 140, y: 200 },
  { x: 120, y: 200 },
]; // Initial snake
let food = { x: 0, y: 0 }; // Food coordinates
let direction = "RIGHT"; // Initial direction
let score = 0;
let gameOver = false;
let gameSpeed = 100; // Default speed (Simple)
let difficulty = 'simple'; // Default difficulty

// Draw snake
function drawSnake() {
  ctx.fillStyle = "lime";
  snake.forEach((segment) => {
    ctx.fillRect(segment.x, segment.y, blockSize, blockSize);
  });
}

// Draw food
function drawFood() {
  ctx.fillStyle = "red";
  ctx.fillRect(food.x, food.y, blockSize, blockSize);
}

// Move the snake
function moveSnake() {
  const head = { ...snake[0] };

  switch (direction) {
    case "UP":
      head.y -= blockSize;
      break;
    case "DOWN":
      head.y += blockSize;
      break;
    case "LEFT":
      head.x -= blockSize;
      break;
    case "RIGHT":
      head.x += blockSize;
      break;
  }

  snake.unshift(head);
  if (head.x === food.x && head.y === food.y) {
    score += 10;
    spawnFood();
  } else {
    snake.pop();
  }
}

// Spawn food at random positions
function spawnFood() {
  food.x = Math.floor(Math.random() * (canvas.width / blockSize)) * blockSize;
  food.y = Math.floor(Math.random() * (canvas.height / blockSize)) * blockSize;
}

// Check if the snake collides with the wall or itself
function checkCollision() {
  const head = snake[0];

  if (
    head.x < 0 ||
    head.x >= canvas.width ||
    head.y < 0 ||
    head.y >= canvas.height
  ) {
    return true;
  }

  for (let i = 1; i < snake.length; i++) {
    if (head.x === snake[i].x && head.y === snake[i].y) {
      return true;
    }
  }

  return false;
}

// Handle keypress for direction change
function handleKeyPress(e) {
  if (gameOver) {
    if (e.code === "Space") {
      resetGame();
    }
    return;
  }

  if (e.code === "ArrowUp" && direction !== "DOWN") {
    direction = "UP";
  } else if (e.code === "ArrowDown" && direction !== "UP") {
    direction = "DOWN";
  } else if (e.code === "ArrowLeft" && direction !== "RIGHT") {
    direction = "LEFT";
  } else if (e.code === "ArrowRight" && direction !== "LEFT") {
    direction = "RIGHT";
  }
}

// Change direction with buttons
function changeDirection(newDirection) {
  if (gameOver) return;

  if (newDirection === "UP" && direction !== "DOWN") {
    direction = "UP";
  } else if (newDirection === "DOWN" && direction !== "UP") {
    direction = "DOWN";
  } else if (newDirection === "LEFT" && direction !== "RIGHT") {
    direction = "LEFT";
  } else if (newDirection === "RIGHT" && direction !== "LEFT") {
    direction = "RIGHT";
  }
}

// Main game loop
function gameLoop() {
  if (gameOver) {
    gameOverText.style.display = "block";
    return;
  }

  ctx.clearRect(0, 0, canvas.width, canvas.height);
  drawSnake();
  drawFood();
  moveSnake();

  if (checkCollision()) {
    gameOver = true;
  }

  setTimeout(gameLoop, gameSpeed);
}

// Reset game
function resetGame() {
  snake = [
    { x: 160, y: 200 },
    { x: 140, y: 200 },
    { x: 120, y: 200 },
  ];
  direction = "RIGHT";
  score = 0;
  gameOver = false;
  gameOverText.style.display = "none";
  spawnFood();
  gameLoop();
}

// Start game with selected difficulty
function startGame(selectedDifficulty) {
  difficulty = selectedDifficulty;
  gameSpeed = difficulty === 'simple' ? 150 : difficulty === 'medium' ? 100 : 60;
  menu.style.display = "none";
  spawnFood();
  gameLoop();
}

// Add keyboard event listener
document.addEventListener("keydown", handleKeyPress);

document.addEventListener("keydown", handleKeyPress);
