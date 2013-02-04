package suntest;
/**
 * This source file is part of project littleaccountbook.
 * This project is under GNU General Public License v2.
 * This Project is a C/S account book.
 * Swing is used to create the GUI of this project and Java DB is used to store data in local machine.
 * Full source code of this project is available at http://littleaccountbook.googlecode.com/svn/trunk/ littleaccountbook-read-only
 * 
 * @author  Moon Zang
 * 
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractIterator<T> implements Iterator<T> {

    T next = nextElement();

    public boolean hasNext() {
        return next != null;
    }

    public T next() {
        if (next == null) {
            throw new NoSuchElementException();
        }
        T result = next;
        next = nextElement();
        return result;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    protected abstract T nextElement();

    private static Iterator<Character> test(final String s) {
        return new AbstractIterator<Character>() {

            private int cursor = 0;

            protected Character nextElement() {
                return cursor == s.length() ? null : s.charAt(cursor++);
            }
        };
    }

    public static void main(String[] args) {
        for (Iterator<Character> i = test("OPS"); i.hasNext();) {
            System.out.print(i.next());
        }
    }
}
