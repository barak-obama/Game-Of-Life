import sys


def __notify__(type, *message):
    """
    This function send a notifications for the user

    :param user: the user whom this function notify
    :type user: globals.User
    :param type: the type of the message
    :type type: str
    :param message: the contact for the message
    :type message: list[str]
    """

    message = list(message)
    for (i, v) in enumerate(message):
        message[i] = str(v)
    message.insert(0, type)
    message = '&&'.join(message)
    print(message)
    sys.stdout.flush()


def notify_error(msg):
    __notify__('ERROR', msg)


def notify_message(msg):
    __notify__('MESSAGE', msg)


def notify_variable(name, value):
    __notify__('VALUE', name, value)
