from errors import *
import importlib
from notification import *
import numpy

server = sys.modules['__main__']

__commands__ = {}


def execute_command(*info):
    info = list(info)
    if info[0] not in __commands__.keys():
        raise NoSuchCommand(info[0])
    current_command = __commands__[info[0]]

    for i in info:
        if i == '':
            info.remove(i)
    if len(info) != len(current_command):
        raise WrongNumberOfArguments(info[0], len(current_command) - 1, len(info) - 1)

    params = []

    for i in xrange(1, len(info)):
        params.append(current_command[i](info[i]))
    current_command[0](*params)


def command(name, *types):
    def command_def(func):
        global __commands__
        __commands__[name] = (func,) + types
        return func

    return command_def


@command('use', str)
def use(func_name):
    server.next_step = importlib.import_module('step_functions.' + func_name).next_step


@command('delay', int)
def delay(delay):
    server.delay = delay
    notify_message(server.delay)


@command('board')
def board():
    numpy.set_printoptions(threshold=numpy.inf)
    board  = str(server.board).replace('\n ', '').replace('\n', '')
    notify_variable('board', board)


@command('set', int, int, int)
def set_val(i, j, val):
    server.board[i, j] = val
    execute_command('board')

@command('eval', str)
def eva(v):
    print(eval(v))
    sys.stdout.flush()


@command('calc')
def clac():
    server.calc()


@command('size', int ,int)
def size(h, w):
    server.board = numpy.matrix(numpy.zeros(shape=(h, w), dtype=int))


@command('start')
def start():
    server.isRunning = True


@command('pause')
def pause():
    server.isRunning = False


@command('exac', str)
def exe(v):
    exec (v)
    sys.stdout.flush()



@command('exit', int)
def quit(a):
    exit(a)

