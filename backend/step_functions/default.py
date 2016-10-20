import itertools


def next_step(board, i, j):
    xMin = max(0, j - 1)
    xMax = min(board.shape[1] - 1, j +1)
    yMin = max(0, i - 1)
    yMax = min(board.shape[0] - 1, i +1)
    iteration = list(itertools.product(range(yMin, yMax + 1), range(xMin, xMax+1)))
    iteration.remove((i,j))
    sum = 0;
    for (k, m) in iteration:
        if board[k, m] == 1:
            sum += 1
    if board[i, j] == 1:
        if sum < 2 or sum > 3:
            return 0
        else:
            return 1
    elif sum == 3:
        return 1
    else:
        return 0
