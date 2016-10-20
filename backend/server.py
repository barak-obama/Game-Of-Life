import itertools
import numpy
import threading
import time
import notification

import commander

delay = 1
low_delay = 1 / 24
board = numpy.matrix([[1, 0, 0], [0, 1, 0], [0, 0, 1]])
next_step = lambda x, i, j: x[i, j]
isRunning = False


def listener():
    while True:
        m = raw_input()  # type: str
        m = m.strip()
        m = m.split(' ')  # type: list[str]
        commander.execute_command(*m)


def calc():
    global board
    new_borad = numpy.matrix(board)
    for (i, j) in list(itertools.product(range(board.shape[0]), range(board.shape[1]))):
        new_borad[i, j] = int(next_step(board, i, j))
    board = new_borad


def calculator():
    global board, next_step
    while True:
        if isRunning:
            calc()
            commander.execute_command('board')
            time.sleep(delay)



listener_thread = threading.Thread(target=listener)
listener_thread.start()
calculator_thread = threading.Thread(target=calculator)
calculator_thread.start()
