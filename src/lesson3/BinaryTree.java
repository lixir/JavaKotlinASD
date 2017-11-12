package lesson3;

import kotlin.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private static class Node<T> {
        private T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) return false;
        Node<T> element = find((T) o);
        if (element == null || element.value != o) return false;

        Node<T> current = element;
        if (element.right != null) {
            current = element.right;
            while (current.left != null) {
                current = current.left;
            }
        } else if (element.left != null){
            current = element.left;
            while (current.right != null) {
                current = current.right;
            }
        } else {
            element = null;
            size--;
            return true;
        }
        element.value = current.value;
        current = null;
        size--;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;

        private boolean flag = true;

        private BinaryTreeIterator() {}

        private Node<T> findNext() {
            if (current == null) {
                current = root;
                while(current.left != null){
                    current = current.left;
                }
                return current;
            }
            if (flag){
                flag = false;
                return current;
            }
            Node<T> temp = current.right;
            if (temp != null){
                while(temp.left != null){
                    temp = temp.left;
                }
            } else {
                temp = findParent(root, current.value);
                if (temp == null) return null;
                if (temp.value.compareTo(current.value) > 0){
                    return temp;
                } else return null;
            }
            return temp;
        }

        private Node<T> findParent(Node<T> parent, T value) {
            int comparison = value.compareTo(parent.value);
            if (comparison < 0) {
                if (parent.left == null) return null;
                if (parent.left.value.compareTo(value) == 0) return parent;
                return findParent(parent.left, value);
            }
            else if (comparison > 0) {
                if (parent.right == null) return null;
                if (parent.right.value.compareTo(value) == 0) return findParent(root, parent.value);
                return findParent(parent.right, value);
            } else {
                return null;
            }
        }

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        SortedSet<T> set = new TreeSet<>();
        for (T element: this){
            set.add(element);
        }
        return set.tailSet(fromElement);
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
