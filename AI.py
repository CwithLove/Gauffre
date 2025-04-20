from typing import List, Tuple, Optional

ROWS = 4
COLS = 6

def create_board(rows: int = ROWS, cols: int = COLS) -> List[List[int]]:
    return [[1 for _ in range(cols)] for _ in range(rows)]

def print_board(board: List[List[int]]):
    rows = len(board)
    cols = len(board[0]) if rows > 0 else 0

    # Print column headers
    print("    " + "  ".join(str(j) for j in range(cols)))

    for i in range(rows):
        row_str = f" {i} "
        for j in range(cols):
            if i == 0 and j == 0:
                cell = "[@]"  # empoisonné
            elif board[i][j] == 1:
                cell = "[#]"
            else:
                cell = "   "
            row_str += cell
        print(row_str)
    print()


def is_game_over(board: List[List[int]]) -> bool:
    return board[0][0] == 0

def apply_move(board: List[List[int]], row: int, col: int) -> List[List[int]]:
    new_board = [r.copy() for r in board]
    for i in range(row, len(new_board)):
        for j in range(col, len(new_board[0])):
            new_board[i][j] = 0
    return new_board

def valid_moves(board: List[List[int]]) -> List[Tuple[int, int]]:
    return [(i, j) for i in range(len(board)) for j in range(len(board[0])) if board[i][j] == 1]

# Updated AI logic with improved evaluation to avoid suicidal moves and enhance decision-making

# Delay-Loss Heuristic
def evaluate(board: List[List[int]], is_ai_turn: bool, depth: int) -> int:
    if is_game_over(board):
        # Nếu AI thua, điểm = -1000 + depth → càng thua muộn, càng tốt
        return (-1000 + depth) if is_ai_turn else (1000 - depth)
    return len(valid_moves(board))


def alphabeta(board: List[List[int]], depth: int, alpha: int, beta: int, is_maximizing: bool) -> int:
    if is_game_over(board) or depth == 0:
        return evaluate(board, is_ai_turn=not is_maximizing, depth=depth)

    if is_maximizing:
        max_eval = float('-inf')
        for move in valid_moves(board):
            eval = alphabeta(apply_move(board, *move), depth - 1, alpha, beta, False)
            max_eval = max(max_eval, eval)
            alpha = max(alpha, eval)
            if beta <= alpha:
                break
        return max_eval
    else:
        min_eval = float('inf')
        for move in valid_moves(board):
            eval = alphabeta(apply_move(board, *move), depth - 1, alpha, beta, True)
            min_eval = min(min_eval, eval)
            beta = min(beta, eval)
            if beta <= alpha:
                break
        return min_eval

def best_move(board: List[List[int]], depth: int = 6) -> Optional[Tuple[int, int]]:
    """Return the best move for AI using Minimax with Alpha-Beta and improved evaluation."""
    best_score = float('-inf')
    move_to_play = None
    for move in valid_moves(board):
        new_board = apply_move(board, *move)
        score = alphabeta(new_board, depth - 1, float('-inf'), float('inf'), False)
        if score > best_score:
            best_score = score
            move_to_play = move
    return move_to_play


def play_game(num_players: int):
    board = create_board()
    turn = 0  # 0 = Player 1, 1 = Player 2 or AI

    while not is_game_over(board):
        print_board(board)
        if num_players == 2 or (num_players == 1 and turn == 0):
            print(f"Player {turn + 1}'s turn.")
            try:
                row = int(input("Enter row: "))
                col = int(input("Enter col: "))
                if (row, col) not in valid_moves(board):
                    print("Invalid move. Try again.")
                    continue
                board = apply_move(board, row, col)
            except:
                print("Invalid input. Try again.")
                continue
        else:
            print("AI's turn.")
            move = best_move(board)
            if move is None:
                print("AI has no move. Game over.")
                break
            print(f"AI chooses: {move}")
            board = apply_move(board, *move)

        if is_game_over(board):
            print_board(board)
            if num_players == 2:
                print(f"Player {((turn + 1) % 2) + 1} wins!")
            else:
                print("You win!" if turn == 1 else "AI wins!")
            return
        turn = (turn + 1) % 2

play_game_code = play_game  # Alias for execution
play_game_code(1)
