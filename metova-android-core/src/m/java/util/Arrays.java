/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package m.java.util;

import m.java.lang.Comparable;

import org.metova.mobile.util.Clones;
import org.metova.mobile.util.iterator.primitive.CharacterArrayIterator;
import org.metova.mobile.util.iterator.primitive.PrimitiveNumberIterator;
import org.metova.mobile.util.iterator.primitive.PrimitiveNumberIterators;

/**
 * Arrays contains static methods which operate on arrays.
 * 
 * @since 1.2
 */
public class Arrays {

    /* Specifies when to switch to insertion sort */
    private static final int SIMPLE_LENGTH = 7;

    /**
     * @param array
     * @param object
     * @return true if the array contains the specified object, false otherwise.
     */
    public static boolean contains( Object[] array, Object object ) {

        for (int i = 0; i < array.length; i++) {

            Object currentElement = array[i];

            if ( object == null ) {
                if ( currentElement == null ) {
                    return true;
                }
            }
            else if ( object.equals( currentElement ) ) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param array
     * @param object
     * @return true if the array contains the specified object, false otherwise.
     */
    public static boolean contains( char[] array, char c ) {

        return PrimitiveNumberIterators.contains( new CharacterArrayIterator( array ), c );
    }

    /**
     * Finds the sum of all the elements of an int array.
     * 
     * @param array int array to find sum of
     * @return the sum of all int elements of the input array
     */
    public static int sum( int[] array ) {

        return sum( array, 0, array.length );
    }

    public static boolean isIndexValid( Object[] objects, int index ) {

        return objects != null && index >= 0 && index < objects.length;
    }

    /**
     * Finds the sum of a range of elements in an int array.
     * 
     * @param array int array
     * @param startingIndex starting index of range
     * @param endingIndex ending index of range
     * @return the sum of the elements between the starting index and the ending index
     */
    public static int sum( int[] array, int startingIndex, int endingIndex ) {

        int sum = 0;

        for (int i = startingIndex; i < endingIndex; i++) {
            sum += array[i];
        }

        return sum;
    }

    public static String toCSV( PrimitiveNumberIterator primitiveNumberIterator ) {

        StringBuffer sb = new StringBuffer();

        if ( primitiveNumberIterator.hasNext() ) {

            double firstNumber = primitiveNumberIterator.nextDouble();
            appendDouble( firstNumber, sb );

            while (primitiveNumberIterator.hasNext()) {
                double next = primitiveNumberIterator.nextDouble();

                sb.append( ',' );
                appendDouble( next, sb );
            }
        }

        return sb.toString();
    }

    private static void appendDouble( double d, StringBuffer sb ) {

        long nextLong = (long) d;

        if ( nextLong == d ) {
            /**
             * The float value is an even number.  We should append the long to the StrinBuffer instead,
             * or we will get a trailing ".0";
             */
            sb.append( nextLong );
        }
        else {
            sb.append( d );
        }
    }

    private static class ArrayList extends AbstractList implements List, RandomAccess {

        private static final long serialVersionUID = -2764017481108945198L;

        private final Object[] a;

        public ArrayList(Object[] storage) {

            if ( storage == null ) {
                throw new NullPointerException();
            }
            a = storage;
        }

        ////@  Override
        public boolean contains( Object object ) {

            if ( object != null ) {
                for (int i = 0; i < a.length; i++) {
                    if ( object.equals( a[i] ) ) {
                        return true;
                    }
                }
            }
            else {
                for (int i = 0; i < a.length; i++) {
                    if ( a[i] == null ) {
                        return true;
                    }
                }
            }
            return false;
        }

        ////@  Override
        public Object get( int location ) {

            try {
                return a[location];
            }
            catch (ArrayIndexOutOfBoundsException e) {
                throw new IndexOutOfBoundsException();
            }
        }

        ////@  Override
        public int indexOf( Object object ) {

            if ( object != null ) {
                for (int i = 0; i < a.length; i++) {
                    if ( object.equals( a[i] ) ) {
                        return i;
                    }
                }
            }
            else {
                for (int i = 0; i < a.length; i++) {
                    if ( a[i] == null ) {
                        return i;
                    }
                }
            }
            return -1;
        }

        ////@  Override
        public int lastIndexOf( Object object ) {

            if ( object != null ) {
                for (int i = a.length - 1; i >= 0; i--) {
                    if ( object.equals( a[i] ) ) {
                        return i;
                    }
                }
            }
            else {
                for (int i = a.length - 1; i >= 0; i--) {
                    if ( a[i] == null ) {
                        return i;
                    }
                }
            }
            return -1;
        }

        ////@  Override
        public Object set( int location, Object object ) {

            try {
                Object result = a[location];
                a[location] = object;
                return result;
            }
            catch (ArrayIndexOutOfBoundsException e) {
                throw new IndexOutOfBoundsException();
            }
            catch (ArrayStoreException e) {
                throw new ClassCastException();
            }
        }

        ////@  Override
        public int size() {

            return a.length;
        }

        ////@  Override
        public Object[] toArray() {

            return Clones.clone( a ); //TODO:NEW
            //            return a.clone(); //TODO:ORIG
        }

        ////@  Override
        ////@  SuppressWarnings("unchecked")
        public Object[] toArray( Object[] contents ) {

            int size = size();
            if ( size > contents.length ) {
                contents = new Object[size];
            }
            System.arraycopy( a, 0, contents, 0, size );
            if ( size < contents.length ) {
                contents[size] = null;
            }
            return contents;
        }
    }

    private Arrays() {

        /* empty */
    }

    /**
     * Answers a List on the objects in the specified array. The size of the
     * List cannot be modified, i.e. adding and removing are unsupported, but
     * the elements can be set. Setting an element modifies the underlying
     * array.
     * 
     * @param array
     *            the array
     * @return a List on the specified array
     */
    public static List asList( Object[] array ) {

        return new ArrayList( array );
    }

    //
    //    /**
    //     * Performs a binary search for the specified element in the specified
    //     * sorted array.
    //     * 
    //     * @param array
    //     *            the sorted byte array to search
    //     * @param value
    //     *            the byte element to find
    //     * @return the non-negative index of the element, or a negative index which
    //     *         is the -index - 1 where the element would be inserted
    //     */
    //    public static int binarySearch( byte[] array, byte value ) {
    //
    //        int low = 0, mid = -1, high = array.length - 1;
    //        while (low <= high) {
    //            mid = ( low + high ) >>> 1;
    //            if (value > array[mid]) {
    //                low = mid + 1;
    //            }
    //            else if (value == array[mid]) {
    //                return mid;
    //            }
    //            else {
    //                high = mid - 1;
    //            }
    //        }
    //        if (mid < 0) {
    //            return -1;
    //        }
    //
    //        return -mid - ( value < array[mid] ? 1 : 2 );
    //    }
    //
    //    /**
    //     * Performs a binary search for the specified element in the specified
    //     * sorted array.
    //     * 
    //     * @param array
    //     *            the sorted char array to search
    //     * @param value
    //     *            the char element to find
    //     * @return the non-negative index of the element, or a negative index which
    //     *         is the -index - 1 where the element would be inserted
    //     */
    //    public static int binarySearch( char[] array, char value ) {
    //
    //        int low = 0, mid = -1, high = array.length - 1;
    //        while (low <= high) {
    //            mid = ( low + high ) >>> 1;
    //            if (value > array[mid]) {
    //                low = mid + 1;
    //            }
    //            else if (value == array[mid]) {
    //                return mid;
    //            }
    //            else {
    //                high = mid - 1;
    //            }
    //        }
    //        if (mid < 0) {
    //            return -1;
    //        }
    //        return -mid - ( value < array[mid] ? 1 : 2 );
    //    }
    //
    //    /**
    //     * Performs a binary search for the specified element in the specified
    //     * sorted array.
    //     * 
    //     * @param array
    //     *            the sorted double array to search
    //     * @param value
    //     *            the double element to find
    //     * @return the non-negative index of the element, or a negative index which
    //     *         is the -index - 1 where the element would be inserted
    //     */
    //    public static int binarySearch( double[] array, double value ) {
    //
    //        long longBits = Double.doubleToLongBits( value );
    //        int low = 0, mid = -1, high = array.length - 1;
    //        while (low <= high) {
    //            mid = ( low + high ) >>> 1;
    //            if (lessThan( array[mid], value )) {
    //                low = mid + 1;
    //            }
    //            else if (longBits == Double.doubleToLongBits( array[mid] )) {
    //                return mid;
    //            }
    //            else {
    //                high = mid - 1;
    //            }
    //        }
    //        if (mid < 0) {
    //            return -1;
    //        }
    //        return -mid - ( lessThan( value, array[mid] ) ? 1 : 2 );
    //    }
    //
    //    /**
    //     * Performs a binary search for the specified element in the specified
    //     * sorted array.
    //     * 
    //     * @param array
    //     *            the sorted float array to search
    //     * @param value
    //     *            the float element to find
    //     * @return the non-negative index of the element, or a negative index which
    //     *         is the -index - 1 where the element would be inserted
    //     */
    //    public static int binarySearch( float[] array, float value ) {
    //
    //        int intBits = Float.floatToIntBits( value );
    //        int low = 0, mid = -1, high = array.length - 1;
    //        while (low <= high) {
    //            mid = ( low + high ) >>> 1;
    //            if (lessThan( array[mid], value )) {
    //                low = mid + 1;
    //            }
    //            else if (intBits == Float.floatToIntBits( array[mid] )) {
    //                return mid;
    //            }
    //            else {
    //                high = mid - 1;
    //            }
    //        }
    //        if (mid < 0) {
    //            return -1;
    //        }
    //        return -mid - ( lessThan( value, array[mid] ) ? 1 : 2 );
    //    }

    /**
     * Performs a binary search for the specified element in the specified
     * sorted array.
     * 
     * @param array
     *            the sorted int array to search
     * @param value
     *            the int element to find
     * @return the non-negative index of the element, or a negative index which
     *         is the -index - 1 where the element would be inserted
     */
    public static int binarySearch( int[] array, int value ) {

        int low = 0, mid = -1, high = array.length - 1;
        while (low <= high) {
            mid = ( low + high ) >>> 1;
            if ( value > array[mid] ) {
                low = mid + 1;
            }
            else if ( value == array[mid] ) {
                return mid;
            }
            else {
                high = mid - 1;
            }
        }
        if ( mid < 0 ) {
            return -1;
        }
        return -mid - ( value < array[mid] ? 1 : 2 );
    }

    //    /**
    //     * Performs a binary search for the specified element in the specified
    //     * sorted array.
    //     * 
    //     * @param array
    //     *            the sorted long array to search
    //     * @param value
    //     *            the long element to find
    //     * @return the non-negative index of the element, or a negative index which
    //     *         is the -index - 1 where the element would be inserted
    //     */
    //    public static int binarySearch( long[] array, long value ) {
    //
    //        int low = 0, mid = -1, high = array.length - 1;
    //        while (low <= high) {
    //            mid = ( low + high ) >>> 1;
    //            if (value > array[mid]) {
    //                low = mid + 1;
    //            }
    //            else if (value == array[mid]) {
    //                return mid;
    //            }
    //            else {
    //                high = mid - 1;
    //            }
    //        }
    //        if (mid < 0) {
    //            return -1;
    //        }
    //        return -mid - ( value < array[mid] ? 1 : 2 );
    //    }
    //
    //    /**
    //     * Performs a binary search for the specified element in the specified
    //     * sorted array.
    //     * 
    //     * @param array
    //     *            the sorted Object array to search
    //     * @param object
    //     *            the Object element to find
    //     * @return the non-negative index of the element, or a negative index which
    //     *         is the -index - 1 where the element would be inserted
    //     * 
    //     * @exception ClassCastException
    //     *                when an element in the array or the search element does
    //     *                not implement Comparable, or cannot be compared to each
    //     *                other
    //     */
    //    ////@  SuppressWarnings("unchecked")
    //    public static int binarySearch( Object[] array, Object object ) {
    //
    //        if (array.length == 0) {
    //            return -1;
    //        }
    //
    //        int low = 0, mid = 0, high = array.length - 1, result = 0;
    //        while (low <= high) {
    //            mid = ( low + high ) >>> 1;
    //            if (( result = ( (Comparable) array[mid] ).compareTo( object ) ) < 0) {
    //                low = mid + 1;
    //            }
    //            else if (result == 0) {
    //                return mid;
    //            }
    //            else {
    //                high = mid - 1;
    //            }
    //        }
    //        return -mid - ( result >= 0 ? 1 : 2 );
    //    }
    //
    //    /**
    //     * Performs a binary search for the specified element in the specified
    //     * sorted array using the PersistableComparator to compare elements.
    //     * 
    //     * @param array
    //     *            the sorted char array to search
    //     * @param object
    //     *            the char element to find
    //     * @param comparator
    //     *            the PersistableComparator
    //     * @return the non-negative index of the element, or a negative index which
    //     *         is the -index - 1 where the element would be inserted
    //     * 
    //     * @exception ClassCastException
    //     *                when an element in the array and the search element cannot
    //     *                be compared to each other using the PersistableComparator
    //     */
    //    public static int binarySearch( Object[] array, Object object, PersistableComparator comparator ) {
    //
    //        if (comparator == null) {
    //            return binarySearch( array, object );
    //        }
    //
    //        int low = 0, mid = 0, high = array.length - 1, result = 0;
    //        while (low <= high) {
    //            mid = ( low + high ) >>> 1;
    //            if (( result = comparator.compare( array[mid], object ) ) < 0) {
    //                low = mid + 1;
    //            }
    //            else if (result == 0) {
    //                return mid;
    //            }
    //            else {
    //                high = mid - 1;
    //            }
    //        }
    //        return -mid - ( result >= 0 ? 1 : 2 );
    //    }
    //
    //    /**
    //     * Performs a binary search for the specified element in the specified
    //     * sorted array.
    //     * 
    //     * @param array
    //     *            the sorted short array to search
    //     * @param value
    //     *            the short element to find
    //     * @return the non-negative index of the element, or a negative index which
    //     *         is the -index - 1 where the element would be inserted
    //     */
    //    public static int binarySearch( short[] array, short value ) {
    //
    //        int low = 0, mid = -1, high = array.length - 1;
    //        while (low <= high) {
    //            mid = ( low + high ) >>> 1;
    //            if (value > array[mid]) {
    //                low = mid + 1;
    //            }
    //            else if (value == array[mid]) {
    //                return mid;
    //            }
    //            else {
    //                high = mid - 1;
    //            }
    //        }
    //        if (mid < 0) {
    //            return -1;
    //        }
    //        return -mid - ( value < array[mid] ? 1 : 2 );
    //    }
    //
    //    /**
    //     * Fills the array with the given value.
    //     * 
    //     * @param array
    //     *            the byte array to fill
    //     * @param value
    //     *            the byte element
    //     */
    //    public static void fill( byte[] array, byte value ) {
    //
    //        for (int i = 0; i < array.length; i++) {
    //            array[i] = value;
    //        }
    //    //    }
    /**
     * Fills the section of the array between the given indices with the given value.
     * 
     * @param array
     *            the byte array to fill
     * @param start
     *            the start index
     * @param end
     *            the end index + 1
     * @param value
     *            the byte element
     * 
     * @exception IllegalArgumentException
     *                when <code>start > end</code>
     * @exception ArrayIndexOutOfBoundsException
     *                when <code>start < 0</code> or
     *                <code>end > array.size()</code>
     */
    public static void fill( byte[] array, int start, int end, byte value ) {

        // Check for null first
        int length = array.length;
        if ( start > end ) {
            throw new IllegalArgumentException();
        }
        if ( start < 0 || end > length ) {
            throw new ArrayIndexOutOfBoundsException();
        }
        for (int i = start; i < end; i++) {
            array[i] = value;
        }
    }

    //
    //    //    /**
    //     * Fills the array with the given value.
    //     * 
    //     * @param array
    //     *            the short array to fill
    //     * @param value
    //     *            the short element
    //     */
    //    public static void fill( short[] array, short value ) {
    //
    //        for (int i = 0; i < array.length; i++) {
    //            array[i] = value;
    //        }
    //    }
    //
    //    /**
    //     * Fills the section of the array between the given indices with the given value.
    //     * 
    //     * @param array
    //     *            the short array to fill
    //     * @param start
    //     *            the start index
    //     * @param end
    //     *            the end index + 1
    //     * @param value
    //     *            the short element
    //     * 
    //     * @exception IllegalArgumentException
    //     *                when <code>start > end</code>
    //     * @exception ArrayIndexOutOfBoundsException
    //     *                when <code>start < 0</code> or
    //     *                <code>end > array.size()</code>
    //     */
    //    public static void fill( short[] array, int start, int end, short value ) {
    //
    //        // Check for null first
    //        int length = array.length;
    //        if (start > end) {
    //            throw new IllegalArgumentException();
    //        }
    //        if (start < 0 || end > length) {
    //            throw new ArrayIndexOutOfBoundsException();
    //        }
    //        for (int i = start; i < end; i++) {
    //            array[i] = value;
    //        }
    //    }

    /**
     * Fills the array with the given value.
     * 
     * @param array
     *            the char array to fill
     * @param value
     *            the char element
     */
    public static void fill( char[] array, char value ) {

        for (int i = 0; i < array.length; i++) {
            array[i] = value;
        }
    }

    /**
     * Fills the section of the array between the given indices with the given value.
     * 
     * @param array
     *            the char array to fill
     * @param start
     *            the start index
     * @param end
     *            the end index + 1
     * @param value
     *            the char element
     * 
     * @exception IllegalArgumentException
     *                when <code>start > end</code>
     * @exception ArrayIndexOutOfBoundsException
     *                when <code>start < 0</code> or
     *                <code>end > array.size()</code>
     */
    public static void fill( char[] array, int start, int end, char value ) {

        // Check for null first
        int length = array.length;
        if ( start > end ) {
            throw new IllegalArgumentException();
        }
        if ( start < 0 || end > length ) {
            throw new ArrayIndexOutOfBoundsException();
        }
        for (int i = start; i < end; i++) {
            array[i] = value;
        }
    }

    /**
     * Fills the array with the given value.
     * 
     * @param array
     *            the int array to fill
     * @param value
     *            the int element
     */
    public static void fill( int[] array, int value ) {

        for (int i = 0; i < array.length; i++) {
            array[i] = value;
        }
    }

    /**
     * Fills the section of the array between the given indices with the given value.
     * 
     * @param array
     *            the int array to fill
     * @param start
     *            the start index
     * @param end
     *            the end index + 1
     * @param value
     *            the int element
     * 
     * @exception IllegalArgumentException
     *                when <code>start > end</code>
     * @exception ArrayIndexOutOfBoundsException
     *                when <code>start < 0</code> or
     *                <code>end > array.size()</code>
     */
    public static void fill( int[] array, int start, int end, int value ) {

        // Check for null first
        int length = array.length;
        if ( start > end ) {
            throw new IllegalArgumentException();
        }
        if ( start < 0 || end > length ) {
            throw new ArrayIndexOutOfBoundsException();
        }
        for (int i = start; i < end; i++) {
            array[i] = value;
        }
    }

    //    /**
    //     * Fills the array with the given value.
    //     * 
    //     * @param array
    //     *            the long array to fill
    //     * @param value
    //     *            the long element
    //     */
    //    public static void fill( long[] array, long value ) {
    //
    //        for (int i = 0; i < array.length; i++) {
    //            array[i] = value;
    //        }
    //    }
    //
    //    /**
    //     * Fills the section of the array between the given indices with the given value.
    //     * 
    //     * @param array
    //     *            the long array to fill
    //     * @param start
    //     *            the start index
    //     * @param end
    //     *            the end index + 1
    //     * @param value
    //     *            the long element
    //     * 
    //     * @exception IllegalArgumentException
    //     *                when <code>start > end</code>
    //     * @exception ArrayIndexOutOfBoundsException
    //     *                when <code>start < 0</code> or
    //     *                <code>end > array.size()</code>
    //     */
    //    public static void fill( long[] array, int start, int end, long value ) {
    //
    //        // Check for null first
    //        int length = array.length;
    //        if (start > end) {
    //            throw new IllegalArgumentException();
    //        }
    //        if (start < 0 || end > length) {
    //            throw new ArrayIndexOutOfBoundsException();
    //        }
    //        for (int i = start; i < end; i++) {
    //            array[i] = value;
    //        }
    //    }
    //
    //    /**
    //     * Fills the array with the given value.
    //     * 
    //     * @param array
    //     *            the float array to fill
    //     * @param value
    //     *            the float element
    //     */
    //    public static void fill( float[] array, float value ) {
    //
    //        for (int i = 0; i < array.length; i++) {
    //            array[i] = value;
    //        }
    //    }
    //
    //    /**
    //     * Fills the section of the array between the given indices with the given value.
    //     * 
    //     * @param array
    //     *            the float array to fill
    //     * @param start
    //     *            the start index
    //     * @param end
    //     *            the end index + 1
    //     * @param value
    //     *            the float element
    //     * 
    //     * @exception IllegalArgumentException
    //     *                when <code>start > end</code>
    //     * @exception ArrayIndexOutOfBoundsException
    //     *                when <code>start < 0</code> or
    //     *                <code>end > array.size()</code>
    //     */
    //    public static void fill( float[] array, int start, int end, float value ) {
    //
    //        // Check for null first
    //        int length = array.length;
    //        if (start > end) {
    //            throw new IllegalArgumentException();
    //        }
    //        if (start < 0 || end > length) {
    //            throw new ArrayIndexOutOfBoundsException();
    //        }
    //        for (int i = start; i < end; i++) {
    //            array[i] = value;
    //        }
    //    }
    //
    //    /**
    //     * Fills the array with the given value.
    //     * 
    //     * @param array
    //     *            the float array to fill
    //     * @param value
    //     *            the float element
    //     */
    //    public static void fill( double[] array, double value ) {
    //
    //        for (int i = 0; i < array.length; i++) {
    //            array[i] = value;
    //        }
    //    }
    //
    //    /**
    //     * Fills the section of the array between the given indices with the given value.
    //     * 
    //     * @param array
    //     *            the double array to fill
    //     * @param start
    //     *            the start index
    //     * @param end
    //     *            the end index + 1
    //     * @param value
    //     *            the double element
    //     * 
    //     * @exception IllegalArgumentException
    //     *                when <code>start > end</code>
    //     * @exception ArrayIndexOutOfBoundsException
    //     *                when <code>start < 0</code> or
    //     *                <code>end > array.size()</code>
    //     */
    //    public static void fill( double[] array, int start, int end, double value ) {
    //
    //        // Check for null first
    //        int length = array.length;
    //        if (start > end) {
    //            throw new IllegalArgumentException();
    //        }
    //        if (start < 0 || end > length) {
    //            throw new ArrayIndexOutOfBoundsException();
    //        }
    //        for (int i = start; i < end; i++) {
    //            array[i] = value;
    //        }
    //    }

    /**
     * Fills the array with the given value.
     * 
     * @param array
     *            the boolean array to fill
     * @param value
     *            the boolean element
     */
    public static void fill( boolean[] array, boolean value ) {

        for (int i = 0; i < array.length; i++) {
            array[i] = value;
        }
    }

    //    /**
    //     * Fills the section of the array between the given indices with the given value.
    //     * 
    //     * @param array
    //     *            the boolean array to fill
    //     * @param start
    //     *            the start index
    //     * @param end
    //     *            the end index + 1
    //     * @param value
    //     *            the boolean element
    //     * 
    //     * @exception IllegalArgumentException
    //     *                when <code>start > end</code>
    //     * @exception ArrayIndexOutOfBoundsException
    //     *                when <code>start < 0</code> or
    //     *                <code>end > array.size()</code>
    //     */
    //    public static void fill( boolean[] array, int start, int end, boolean value ) {
    //
    //        // Check for null first
    //        int length = array.length;
    //        if (start > end) {
    //            throw new IllegalArgumentException();
    //        }
    //        if (start < 0 || end > length) {
    //            throw new ArrayIndexOutOfBoundsException();
    //        }
    //        for (int i = start; i < end; i++) {
    //            array[i] = value;
    //        }
    //    }

    /**
     * Fills the array with the given value.
     * 
     * @param array
     *            the Object array to fill
     * @param value
     *            the Object element
     */
    public static void fill( Object[] array, Object value ) {

        for (int i = 0; i < array.length; i++) {
            array[i] = value;
        }
    }

    /**
     * Fills the section of the array between the given indices with the given value.
     * 
     * @param array
     *            the Object array to fill
     * @param start
     *            the start index
     * @param end
     *            the end index + 1
     * @param value
     *            the Object element
     * 
     * @exception IllegalArgumentException
     *                when <code>start > end</code>
     * @exception ArrayIndexOutOfBoundsException
     *                when <code>start < 0</code> or
     *                <code>end > array.size()</code>
     */
    public static void fill( Object[] array, int start, int end, Object value ) {

        // Check for null first
        int length = array.length;
        if ( start > end ) {
            throw new IllegalArgumentException();
        }
        if ( start < 0 || end > length ) {
            throw new ArrayIndexOutOfBoundsException();
        }
        for (int i = start; i < end; i++) {
            array[i] = value;
        }
    }

    //    /**
    //     * Returns the hash code for the given array.
    //     * 
    //     * If Arrays.equals(...) returns true for two arrays then their hash codes
    //     * will also be equal.
    //     *  
    //     * The value returned by this method is the same value as the
    //     * {@link List#hashCode()}} method which is invoked on a {@link List}}
    //     * containing a sequence of {@link Boolean}} instances representing the
    //     * elements of array in the same order. 
    //     * 
    //     * If the array is null the return value will be 0.
    //     * 
    //     * @param array
    //     *            the array to return the hash code for
    //     * @return the hash code for array
    //     */
    //    public static int hashCode( boolean[] array ) {
    //
    //        if (array == null) {
    //            return 0;
    //        }
    //        int hashCode = 1;
    //        for (int i = 0; i < array.length; i++) {
    //            // 1231, 1237 are hash code values for boolean value
    //            hashCode = 31 * hashCode + ( array[i] ? 1231 : 1237 );
    //        }
    //        return hashCode;
    //    }
    //
    //    /**
    //     * Returns the hash code for the given array.
    //     * 
    //     * If Arrays.equals(...) returns true for two arrays then their hash codes
    //     * will also be equal.
    //     * 
    //     * The value returned by this method is the same value as the
    //     * {@link List#hashCode()}} method which is invoked on a {@link List}}
    //     * containing a sequence of {@link Integer}} instances representing the
    //     * elements of array in the same order. 
    //     * 
    //     * If the array is null the return value will be 0.
    //     * 
    //     * @param array
    //     *            the array to return the hash code for
    //     * @return the hash code for array
    //     */
    //    public static int hashCode( int[] array ) {
    //
    //        if (array == null) {
    //            return 0;
    //        }
    //        int hashCode = 1;
    //        for (int i = 0; i < array.length; i++) {
    //            // the hash code value for integer value is integer value itself
    //            hashCode = 31 * hashCode + array[i];
    //        }
    //        return hashCode;
    //    }
    //
    //    /**
    //     * Returns the hash code for the given array.
    //     * 
    //     * If Arrays.equals(...) returns true for two arrays then their hash codes
    //     * will also be equal.
    //     * 
    //     * The value returned by this method is the same value as the
    //     * {@link List#hashCode()}} method which is invoked on a {@link List}}
    //     * containing a sequence of {@link Short}} instances representing the
    //     * elements of array in the same order. 
    //     * 
    //     * If the array is null the return value will be 0.
    //     * 
    //     * @param array
    //     *            the array to return the hash code for
    //     * @return the hash code for array
    //     */
    //    public static int hashCode( short[] array ) {
    //
    //        if (array == null) {
    //            return 0;
    //        }
    //        int hashCode = 1;
    //        for (int i = 0; i < array.length; i++) {
    //            // the hash code value for short value is its integer value
    //            hashCode = 31 * hashCode + array[i];
    //        }
    //        return hashCode;
    //    }
    //
    //    /**
    //     * Returns the hash code for the given array.
    //     * 
    //     * If Arrays.equals(...) returns true for two arrays then their hash codes
    //     * will also be equal.
    //     *  
    //     * The value returned by this method is the same value as the
    //     * {@link List#hashCode()}} method which is invoked on a {@link List}}
    //     * containing a sequence of {@link Character}} instances representing the
    //     * elements of array in the same order. 
    //     * 
    //     * If the array is null the return value will be 0.
    //     * 
    //     * @param array
    //     *            the array to return the hash code for
    //     * @return the hash code for array
    //     */
    //    public static int hashCode( char[] array ) {
    //
    //        if (array == null) {
    //            return 0;
    //        }
    //        int hashCode = 1;
    //        for (int i = 0; i < array.length; i++) {
    //            // the hash code value for char value is its integer value
    //            hashCode = 31 * hashCode + array[i];
    //        }
    //        return hashCode;
    //    }
    //
    //    /**
    //     * Returns the hash code for the given array.
    //     * 
    //     * If Arrays.equals(...) returns true for two arrays then their hash codes
    //     * will also be equal.
    //     *  
    //     * The value returned by this method is the same value as the
    //     * {@link List#hashCode()}} method which is invoked on a {@link List}}
    //     * containing a sequence of {@link Byte}} instances representing the
    //     * elements of array in the same order. 
    //     * 
    //     * If the array is null the return value will be 0.
    //     * 
    //     * @param array
    //     *            the array to return the hash code for
    //     * @return the hash code for array
    //     */
    //    public static int hashCode( byte[] array ) {
    //
    //        if (array == null) {
    //            return 0;
    //        }
    //        int hashCode = 1;
    //        for (int i = 0; i < array.length; i++) {
    //            // the hash code value for byte value is its integer value
    //            hashCode = 31 * hashCode + array[i];
    //        }
    //        return hashCode;
    //    }
    //
    //    /**
    //     * Returns the hash code for the given array.
    //     * 
    //     * If Arrays.equals(...) returns true for two arrays then their hash codes
    //     * will also be equal.
    //     *  
    //     * The value returned by this method is the same value as the
    //     * {@link List#hashCode()}} method which is invoked on a {@link List}}
    //     * containing a sequence of {@link Long}} instances representing the
    //     * elements of array in the same order. 
    //     * 
    //     * If the array is null the return value will be 0.
    //     * 
    //     * @param array
    //     *            the array to return the hash code for
    //     * @return the hash code for array
    //     */
    //    public static int hashCode( long[] array ) {
    //
    //        if (array == null) {
    //            return 0;
    //        }
    //        int hashCode = 1;
    //        for (int i = 0; i < array.length; i++) {
    //            /*
    //             * the hash code value for long value is (int) (value ^ (value >>>
    //             * 32))
    //             */
    //            hashCode = 31 * hashCode + (int) ( array[i] ^ ( array[i] >>> 32 ) );
    //        }
    //        return hashCode;
    //    }
    //
    //    /**
    //     * Returns the hash code for the given array.
    //     * 
    //     * If Arrays.equals(...) returns true for two arrays then their hash codes
    //     * will also be equal.
    //     *  
    //     * The value returned by this method is the same value as the
    //     * {@link List#hashCode()}} method which is invoked on a {@link List}}
    //     * containing a sequence of {@link Float}} instances representing the
    //     * elements of array in the same order. 
    //     * 
    //     * If the array is null the return value will be 0.
    //     * 
    //     * @param array
    //     *            the array to return the hash code for
    //     * @return the hash code for array
    //     */
    //    public static int hashCode( float[] array ) {
    //
    //        if (array == null) {
    //            return 0;
    //        }
    //        int hashCode = 1;
    //        for (int i = 0; i < array.length; i++) {
    //            /*
    //             * the hash code value for float value is
    //             * Float.floatToIntBits(value)
    //             */
    //            hashCode = 31 * hashCode + Float.floatToIntBits( array[i] );
    //        }
    //        return hashCode;
    //    }
    //
    //    /**
    //     * Returns the hash code for the given array.
    //     * 
    //     * If Arrays.equals(...) returns true for two arrays then their hash codes
    //     * will also be equal.
    //     * 
    //     * The value returned by this method is the same value as the
    //     * {@link List#hashCode()}} method which is invoked on a {@link List}}
    //     * containing a sequence of {@link Double}} instances representing the
    //     * elements of array in the same order. 
    //     * 
    //     * If the array is null, the return value will be 0.
    //     * 
    //     * @param array
    //     *            the array to return the hash code for
    //     * @return the hash code for array
    //     */
    //    public static int hashCode( double[] array ) {
    //
    //        if (array == null) {
    //            return 0;
    //        }
    //        int hashCode = 1;
    //
    //        for (int i = 0; i < array.length; i++) {
    //            long v = Double.doubleToLongBits( array[i] );
    //            /*
    //             * the hash code value for double value is (int) (v ^ (v >>> 32))
    //             * where v = Double.doubleToLongBits(value)
    //             */
    //            hashCode = 31 * hashCode + (int) ( v ^ ( v >>> 32 ) );
    //        }
    //        return hashCode;
    //    }
    //
    //    /**
    //     * Returns the hash code for the given array. If this array contains other
    //     * arrays, their contents will not be recursively searched so this method
    //     * should be used if the array is likely to contain a reference to itself.
    //     * 
    //     * If Arrays.equals(...) returns true for two arrays then their hash codes
    //     * will also be equal.
    //     * 
    //     * The value returned by this method is the same value as the method
    //     * Arrays.asList(array).hashCode(). 
    //     * 
    //     * If the array is null, the return value will be 0.
    //     * 
    //     * @param array
    //     *            the array to return the hash code for
    //     * @return the hash code for array
    //     */
    //    public static int hashCode( Object[] array ) {
    //
    //        if (array == null) {
    //            return 0;
    //        }
    //        int hashCode = 1;
    //        for (int i = 0; i < array.length; i++) {
    //            int elementHashCode;
    //
    //            if (array[i] == null) {
    //                elementHashCode = 0;
    //            }
    //            else {
    //                elementHashCode = ( array[i] ).hashCode();
    //            }
    //            hashCode = 31 * hashCode + elementHashCode;
    //        }
    //        return hashCode;
    //    }
    //
    //    //TODO:ORIG
    //    //    /**
    //    //     * Returns the 'deep' hash code for the given array. This means that if this
    //    //     * array contains other arrays their contents will also be included in the
    //    //     * hash and so on recursively. This method should not be used if the array
    //    //     * or any arrays contained within it are likely to contain a reference to
    //    //     * itself.
    //    //     * 
    //    //     * If Arrays.deepEquals(...) returns true for two arrays then their deep
    //    //     * hash codes will also be equal.
    //    //     * 
    //    //     * If the array is null the return value will be 0.
    //    //     * 
    //    //     * @param array
    //    //     *            the array to return the hash code for
    //    //     * @return the hash code for array
    //    //     */
    //    //    public static int deepHashCode( Object[] array ) {
    //    //
    //    //        if (array == null) {
    //    //            return 0;
    //    //        }
    //    //        int hashCode = 1;
    //    //        for (int i = 0; i < array.length; i++) {
    //    //            int elementHashCode = deepHashCodeElement( array[i] );
    //    //            hashCode = 31 * hashCode + elementHashCode;
    //    //        }
    //    //        return hashCode;
    //    //    }
    //
    //    //TODO:ORIG
    //    //    private static int deepHashCodeElement( Object element ) {
    //    //
    //    //        Class cl;
    //    //        if (element == null) {
    //    //            return 0;
    //    //        }
    //    //
    //    //        cl = element.getClass().getComponentType();
    //    //
    //    //        if (cl == null) {
    //    //            return element.hashCode();
    //    //        }
    //    //
    //    //        /*
    //    //         * element is an array
    //    //         */
    //    //        if (!cl.isPrimitive()) {
    //    //            return deepHashCode( (Object[]) element );
    //    //        }
    //    //        if (cl.equals( int.class )) {
    //    //            return hashCode( (int[]) element );
    //    //        }
    //    //        if (cl.equals( char.class )) {
    //    //            return hashCode( (char[]) element );
    //    //        }
    //    //        if (cl.equals( boolean.class )) {
    //    //            return hashCode( (boolean[]) element );
    //    //        }
    //    //        if (cl.equals( byte.class )) {
    //    //            return hashCode( (byte[]) element );
    //    //        }
    //    //        if (cl.equals( long.class )) {
    //    //            return hashCode( (long[]) element );
    //    //        }
    //    //        if (cl.equals( float.class )) {
    //    //            return hashCode( (float[]) element );
    //    //        }
    //    //        if (cl.equals( double.class )) {
    //    //            return hashCode( (double[]) element );
    //    //        }
    //    //        return hashCode( (short[]) element );
    //    //    }

    /**
     * Compares the two arrays.
     * 
     * @param array1
     *            the first byte array
     * @param array2
     *            the second byte array
     * @return true when the arrays have the same length and the elements at
     *         each index in the two arrays are equal, false otherwise
     */
    public static boolean equals( byte[] array1, byte[] array2 ) {

        if ( array1 == array2 ) {
            return true;
        }
        if ( array1 == null || array2 == null || array1.length != array2.length ) {
            return false;
        }
        for (int i = 0; i < array1.length; i++) {
            if ( array1[i] != array2[i] ) {
                return false;
            }
        }
        return true;
    }

    //    /**
    //     * Compares the two arrays.
    //     * 
    //     * @param array1
    //     *            the first short array
    //     * @param array2
    //     *            the second short array
    //     * @return true when the arrays have the same length and the elements at
    //     *         each index in the two arrays are equal, false otherwise
    //     */
    //    public static boolean equals( short[] array1, short[] array2 ) {
    //
    //        if (array1 == array2) {
    //            return true;
    //        }
    //        if (array1 == null || array2 == null || array1.length != array2.length) {
    //            return false;
    //        }
    //        for (int i = 0; i < array1.length; i++) {
    //            if (array1[i] != array2[i]) {
    //                return false;
    //            }
    //        }
    //        return true;
    //    }
    //
    //    /**
    //     * Compares the two arrays.
    //     * 
    //     * @param array1
    //     *            the first char array
    //     * @param array2
    //     *            the second char array
    //     * @return true when the arrays have the same length and the elements at
    //     *         each index in the two arrays are equal, false otherwise
    //     */
    //    public static boolean equals( char[] array1, char[] array2 ) {
    //
    //        if (array1 == array2) {
    //            return true;
    //        }
    //        if (array1 == null || array2 == null || array1.length != array2.length) {
    //            return false;
    //        }
    //        for (int i = 0; i < array1.length; i++) {
    //            if (array1[i] != array2[i]) {
    //                return false;
    //            }
    //        }
    //        return true;
    //    }
    //
    //    /**
    //     * Compares the two arrays.
    //     * 
    //     * @param array1
    //     *            the first int array
    //     * @param array2
    //     *            the second int array
    //     * @return true when the arrays have the same length and the elements at
    //     *         each index in the two arrays are equal, false otherwise
    //     */
    //    public static boolean equals( int[] array1, int[] array2 ) {
    //
    //        if (array1 == array2) {
    //            return true;
    //        }
    //        if (array1 == null || array2 == null || array1.length != array2.length) {
    //            return false;
    //        }
    //        for (int i = 0; i < array1.length; i++) {
    //            if (array1[i] != array2[i]) {
    //                return false;
    //            }
    //        }
    //        return true;
    //    }
    //
    //    /**
    //     * Compares the two arrays.
    //     * 
    //     * @param array1
    //     *            the first long array
    //     * @param array2
    //     *            the second long array
    //     * @return true when the arrays have the same length and the elements at
    //     *         each index in the two arrays are equal, false otherwise
    //     */
    //    public static boolean equals( long[] array1, long[] array2 ) {
    //
    //        if (array1 == array2) {
    //            return true;
    //        }
    //        if (array1 == null || array2 == null || array1.length != array2.length) {
    //            return false;
    //        }
    //        for (int i = 0; i < array1.length; i++) {
    //            if (array1[i] != array2[i]) {
    //                return false;
    //            }
    //        }
    //        return true;
    //    }
    //
    //    /**
    //     * Compares the two arrays. The values are compared in the same manner as
    //     * Float.equals().
    //     * 
    //     * @param array1
    //     *            the first float array
    //     * @param array2
    //     *            the second float array
    //     * @return true when the arrays have the same length and the elements at
    //     *         each index in the two arrays are equal, false otherwise
    //     * 
    //     * @see Float#equals(Object)
    //     */
    //    public static boolean equals( float[] array1, float[] array2 ) {
    //
    //        if (array1 == array2) {
    //            return true;
    //        }
    //        if (array1 == null || array2 == null || array1.length != array2.length) {
    //            return false;
    //        }
    //        for (int i = 0; i < array1.length; i++) {
    //            if (Float.floatToIntBits( array1[i] ) != Float.floatToIntBits( array2[i] )) {
    //                return false;
    //            }
    //        }
    //        return true;
    //    }
    //
    //    /**
    //     * Compares the two arrays. The values are compared in the same manner as
    //     * Double.equals().
    //     * 
    //     * @param array1
    //     *            the first double array
    //     * @param array2
    //     *            the second double array
    //     * @return true when the arrays have the same length and the elements at
    //     *         each index in the two arrays are equal, false otherwise
    //     * 
    //     * @see Double#equals(Object)
    //     */
    //    public static boolean equals( double[] array1, double[] array2 ) {
    //
    //        if (array1 == array2) {
    //            return true;
    //        }
    //        if (array1 == null || array2 == null || array1.length != array2.length) {
    //            return false;
    //        }
    //        for (int i = 0; i < array1.length; i++) {
    //            if (Double.doubleToLongBits( array1[i] ) != Double.doubleToLongBits( array2[i] )) {
    //                return false;
    //            }
    //        }
    //        return true;
    //    }
    //
    //    /**
    //     * Compares the two arrays.
    //     * 
    //     * @param array1
    //     *            the first boolean array
    //     * @param array2
    //     *            the second boolean array
    //     * @return true when the arrays have the same length and the elements at
    //     *         each index in the two arrays are equal, false otherwise
    //     */
    //    public static boolean equals( boolean[] array1, boolean[] array2 ) {
    //
    //        if (array1 == array2) {
    //            return true;
    //        }
    //        if (array1 == null || array2 == null || array1.length != array2.length) {
    //            return false;
    //        }
    //        for (int i = 0; i < array1.length; i++) {
    //            if (array1[i] != array2[i]) {
    //                return false;
    //            }
    //        }
    //        return true;
    //    }

    /**
     * Compares the two arrays.
     * 
     * @param array1
     *            the first Object array
     * @param array2
     *            the second Object array
     * @return true when the arrays have the same length and the elements at
     *         each index in the two arrays are equal, false otherwise
     */
    public static boolean equals( Object[] array1, Object[] array2 ) {

        if ( array1 == array2 ) {
            return true;
        }
        if ( array1 == null || array2 == null || array1.length != array2.length ) {
            return false;
        }
        for (int i = 0; i < array1.length; i++) {
            Object e1 = array1[i], e2 = array2[i];
            if ( !( e1 == null ? e2 == null : e1.equals( e2 ) ) ) {
                return false;
            }
        }
        return true;
    }

    //    //TODO:ORIG
    //    //    /**
    //    //     * Returns the 'deep' equals for the two given arrays. This means that if
    //    //     * either of the arrays contains other arrays then their contents are also
    //    //     * checked for (deep) equality. If two corresponding elements are arrays of
    //    //     * a primitive type then the appropriate <code>Arrays.equals</code> method
    //    //     * is used to determine their equality. Otherwise for two
    //    //     * <code>Object</code> arrays <code>deepEquals</code> is called
    //    //     * recursively.
    //    //     * 
    //    //     * This method should not be used if either of the arrays, or any arrays
    //    //     * contained within it are likely to contain a reference to itself.
    //    //     * 
    //    //     * @param array1
    //    //     *            the first Object array
    //    //     * @param array2
    //    //     *            the second Object array
    //    //     * @return true when the arrays have the same length and the elements at
    //    //     *         each index in the two arrays are equal, false otherwise
    //    //     */
    //    //    public static boolean deepEquals( Object[] array1, Object[] array2 ) {
    //    //
    //    //        if (array1 == array2) {
    //    //            return true;
    //    //        }
    //    //        if (array1 == null || array2 == null || array1.length != array2.length) {
    //    //            return false;
    //    //        }
    //    //        for (int i = 0; i < array1.length; i++) {
    //    //            Object e1 = array1[i], e2 = array2[i];
    //    //
    //    //            if (!deepEqualsElements( e1, e2 )) {
    //    //                return false;
    //    //            }
    //    //        }
    //    //        return true;
    //    //    }
    //
    //    //TODO:ORIG
    //    //    private static boolean deepEqualsElements( Object e1, Object e2 ) {
    //    //
    //    //        Class cl1, cl2;
    //    //
    //    //        if (e1 == e2) {
    //    //            return true;
    //    //        }
    //    //
    //    //        if (e1 == null || e2 == null) {
    //    //            return false;
    //    //        }
    //    //
    //    //        cl1 = e1.getClass().getComponentType();
    //    //        cl2 = e2.getClass().getComponentType();
    //    //
    //    //        if (cl1 != cl2) {
    //    //            return false;
    //    //        }
    //    //
    //    //        if (cl1 == null) {
    //    //            return e1.equals( e2 );
    //    //        }
    //    //
    //    //        /*
    //    //         * compare as arrays
    //    //         */
    //    //        if (!cl1.isPrimitive()) {
    //    //            return deepEquals( (Object[]) e1, (Object[]) e2 );
    //    //        }
    //    //
    //    //        if (cl1.equals( int.class )) {
    //    //            return equals( (int[]) e1, (int[]) e2 );
    //    //        }
    //    //        if (cl1.equals( char.class )) {
    //    //            return equals( (char[]) e1, (char[]) e2 );
    //    //        }
    //    //        if (cl1.equals( boolean.class )) {
    //    //            return equals( (boolean[]) e1, (boolean[]) e2 );
    //    //        }
    //    //        if (cl1.equals( byte.class )) {
    //    //            return equals( (byte[]) e1, (byte[]) e2 );
    //    //        }
    //    //        if (cl1.equals( long.class )) {
    //    //            return equals( (long[]) e1, (long[]) e2 );
    //    //        }
    //    //        if (cl1.equals( float.class )) {
    //    //            return equals( (float[]) e1, (float[]) e2 );
    //    //        }
    //    //        if (cl1.equals( double.class )) {
    //    //            return equals( (double[]) e1, (double[]) e2 );
    //    //        }
    //    //        return equals( (short[]) e1, (short[]) e2 );
    //    //    }
    //
    //    private static boolean lessThan( double double1, double double2 ) {
    //
    //        long d1, d2;
    //        long NaNbits = Double.doubleToLongBits( Double.NaN );
    //        if (( d1 = Double.doubleToLongBits( double1 ) ) == NaNbits) {
    //            return false;
    //        }
    //        if (( d2 = Double.doubleToLongBits( double2 ) ) == NaNbits) {
    //            return true;
    //        }
    //        if (double1 == double2) {
    //            if (d1 == d2) {
    //                return false;
    //            }
    //            // check for -0
    //            return d1 < d2;
    //        }
    //        return double1 < double2;
    //    }
    //
    //    private static boolean lessThan( float float1, float float2 ) {
    //
    //        int f1, f2;
    //        int NaNbits = Float.floatToIntBits( Float.NaN );
    //        if (( f1 = Float.floatToIntBits( float1 ) ) == NaNbits) {
    //            return false;
    //        }
    //        if (( f2 = Float.floatToIntBits( float2 ) ) == NaNbits) {
    //            return true;
    //        }
    //        if (float1 == float2) {
    //            if (f1 == f2) {
    //                return false;
    //            }
    //            // check for -0
    //            return f1 < f2;
    //        }
    //        return float1 < float2;
    //    }
    //
    //    private static int med3( byte[] array, int a, int b, int c ) {
    //
    //        byte x = array[a], y = array[b], z = array[c];
    //        return x < y ? ( y < z ? b : ( x < z ? c : a ) ) : ( y > z ? b : ( x > z ? c : a ) );
    //    }
    //
    //    private static int med3( char[] array, int a, int b, int c ) {
    //
    //        char x = array[a], y = array[b], z = array[c];
    //        return x < y ? ( y < z ? b : ( x < z ? c : a ) ) : ( y > z ? b : ( x > z ? c : a ) );
    //    }
    //
    //    private static int med3( double[] array, int a, int b, int c ) {
    //
    //        double x = array[a], y = array[b], z = array[c];
    //        return lessThan( x, y ) ? ( lessThan( y, z ) ? b : ( lessThan( x, z ) ? c : a ) ) : ( lessThan( z, y ) ? b : ( lessThan( z, x ) ? c : a ) );
    //    }
    //
    //    private static int med3( float[] array, int a, int b, int c ) {
    //
    //        float x = array[a], y = array[b], z = array[c];
    //        return lessThan( x, y ) ? ( lessThan( y, z ) ? b : ( lessThan( x, z ) ? c : a ) ) : ( lessThan( z, y ) ? b : ( lessThan( z, x ) ? c : a ) );
    //    }
    //
    //    private static int med3( int[] array, int a, int b, int c ) {
    //
    //        int x = array[a], y = array[b], z = array[c];
    //        return x < y ? ( y < z ? b : ( x < z ? c : a ) ) : ( y > z ? b : ( x > z ? c : a ) );
    //    }
    //
    //    private static int med3( long[] array, int a, int b, int c ) {
    //
    //        long x = array[a], y = array[b], z = array[c];
    //        return x < y ? ( y < z ? b : ( x < z ? c : a ) ) : ( y > z ? b : ( x > z ? c : a ) );
    //    }
    //
    //    private static int med3( short[] array, int a, int b, int c ) {
    //
    //        short x = array[a], y = array[b], z = array[c];
    //        return x < y ? ( y < z ? b : ( x < z ? c : a ) ) : ( y > z ? b : ( x > z ? c : a ) );
    //    }
    //
    //    /**
    //     * Performs a sort on the given array. Elements will be re-ordered into
    //     * ascending order.
    //     * 
    //     * @param array
    //     *            the byte array to sort
    //     */
    //    public static void sort( byte[] array ) {
    //
    //        sort( 0, array.length, array );
    //    }
    //
    //    /**
    //     * Performs a sort on the section of the array between the given indices.
    //     * Elements will be re-ordered into ascending order.
    //     * 
    //     * @param array
    //     *            the byte array to sort
    //     * @param start
    //     *            the start index
    //     * @param end
    //     *            the end index + 1
    //     * 
    //     * @exception IllegalArgumentException
    //     *                when <code>start > end</code>
    //     * @exception ArrayIndexOutOfBoundsException
    //     *                when <code>start < 0</code> or
    //     *                <code>end > array.size()</code>
    //     */
    //    public static void sort( byte[] array, int start, int end ) {
    //
    //        if (array == null) {
    //            throw new NullPointerException();
    //        }
    //        checkBounds( array.length, start, end );
    //        sort( start, end, array );
    //    }

    private static void checkBounds( int arrLength, int start, int end ) {

        if ( start > end ) {
            throw new IllegalArgumentException( "start(" + start //$NON-NLS-1$
                    + ") > end(" + end + ")" ); //$NON-NLS-1$ //$NON-NLS-2$
        }
        if ( start < 0 || end > arrLength ) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    //    private static void sort( int start, int end, byte[] array ) {
    //
    //        byte temp;
    //        int length = end - start;
    //        if (length < 7) {
    //            for (int i = start + 1; i < end; i++) {
    //                for (int j = i; j > start && array[j - 1] > array[j]; j--) {
    //                    temp = array[j];
    //                    array[j] = array[j - 1];
    //                    array[j - 1] = temp;
    //                }
    //            }
    //            return;
    //        }
    //        int middle = ( start + end ) / 2;
    //        if (length > 7) {
    //            int bottom = start;
    //            int top = end - 1;
    //            if (length > 40) {
    //                length /= 8;
    //                bottom = med3( array, bottom, bottom + length, bottom + ( 2 * length ) );
    //                middle = med3( array, middle - length, middle, middle + length );
    //                top = med3( array, top - ( 2 * length ), top - length, top );
    //            }
    //            middle = med3( array, bottom, middle, top );
    //        }
    //        byte partionValue = array[middle];
    //        int a, b, c, d;
    //        a = b = start;
    //        c = d = end - 1;
    //        while (true) {
    //            while (b <= c && array[b] <= partionValue) {
    //                if (array[b] == partionValue) {
    //                    temp = array[a];
    //                    array[a++] = array[b];
    //                    array[b] = temp;
    //                }
    //                b++;
    //            }
    //            while (c >= b && array[c] >= partionValue) {
    //                if (array[c] == partionValue) {
    //                    temp = array[c];
    //                    array[c] = array[d];
    //                    array[d--] = temp;
    //                }
    //                c--;
    //            }
    //            if (b > c) {
    //                break;
    //            }
    //            temp = array[b];
    //            array[b++] = array[c];
    //            array[c--] = temp;
    //        }
    //        length = a - start < b - a ? a - start : b - a;
    //        int l = start;
    //        int h = b - length;
    //        while (length-- > 0) {
    //            temp = array[l];
    //            array[l++] = array[h];
    //            array[h++] = temp;
    //        }
    //        length = d - c < end - 1 - d ? d - c : end - 1 - d;
    //        l = b;
    //        h = end - length;
    //        while (length-- > 0) {
    //            temp = array[l];
    //            array[l++] = array[h];
    //            array[h++] = temp;
    //        }
    //        if (( length = b - a ) > 0) {
    //            sort( start, start + length, array );
    //        }
    //        if (( length = d - c ) > 0) {
    //            sort( end - length, end, array );
    //        }
    //    }
    //
    //    /**
    //     * Performs a sort on the given array. Elements will be re-ordered into
    //     * ascending order.
    //     * 
    //     * @param array
    //     *            the char array to sort
    //     */
    //    public static void sort( char[] array ) {
    //
    //        sort( 0, array.length, array );
    //    }
    //
    //    /**
    //     * Performs a sort on the section of the array between the given indices.
    //     * Elements will be re-ordered into ascending order.
    //     * 
    //     * @param array
    //     *            the char array to sort
    //     * @param start
    //     *            the start index
    //     * @param end
    //     *            the end index + 1
    //     * 
    //     * @exception IllegalArgumentException
    //     *                when <code>start > end</code>
    //     * @exception ArrayIndexOutOfBoundsException
    //     *                when <code>start < 0</code> or
    //     *                <code>end > array.size()</code>
    //     */
    //    public static void sort( char[] array, int start, int end ) {
    //
    //        if (array == null) {
    //            throw new NullPointerException();
    //        }
    //        checkBounds( array.length, start, end );
    //        sort( start, end, array );
    //    }
    //
    //    private static void sort( int start, int end, char[] array ) {
    //
    //        char temp;
    //        int length = end - start;
    //        if (length < 7) {
    //            for (int i = start + 1; i < end; i++) {
    //                for (int j = i; j > start && array[j - 1] > array[j]; j--) {
    //                    temp = array[j];
    //                    array[j] = array[j - 1];
    //                    array[j - 1] = temp;
    //                }
    //            }
    //            return;
    //        }
    //        int middle = ( start + end ) / 2;
    //        if (length > 7) {
    //            int bottom = start;
    //            int top = end - 1;
    //            if (length > 40) {
    //                length /= 8;
    //                bottom = med3( array, bottom, bottom + length, bottom + ( 2 * length ) );
    //                middle = med3( array, middle - length, middle, middle + length );
    //                top = med3( array, top - ( 2 * length ), top - length, top );
    //            }
    //            middle = med3( array, bottom, middle, top );
    //        }
    //        char partionValue = array[middle];
    //        int a, b, c, d;
    //        a = b = start;
    //        c = d = end - 1;
    //        while (true) {
    //            while (b <= c && array[b] <= partionValue) {
    //                if (array[b] == partionValue) {
    //                    temp = array[a];
    //                    array[a++] = array[b];
    //                    array[b] = temp;
    //                }
    //                b++;
    //            }
    //            while (c >= b && array[c] >= partionValue) {
    //                if (array[c] == partionValue) {
    //                    temp = array[c];
    //                    array[c] = array[d];
    //                    array[d--] = temp;
    //                }
    //                c--;
    //            }
    //            if (b > c) {
    //                break;
    //            }
    //            temp = array[b];
    //            array[b++] = array[c];
    //            array[c--] = temp;
    //        }
    //        length = a - start < b - a ? a - start : b - a;
    //        int l = start;
    //        int h = b - length;
    //        while (length-- > 0) {
    //            temp = array[l];
    //            array[l++] = array[h];
    //            array[h++] = temp;
    //        }
    //        length = d - c < end - 1 - d ? d - c : end - 1 - d;
    //        l = b;
    //        h = end - length;
    //        while (length-- > 0) {
    //            temp = array[l];
    //            array[l++] = array[h];
    //            array[h++] = temp;
    //        }
    //        if (( length = b - a ) > 0) {
    //            sort( start, start + length, array );
    //        }
    //        if (( length = d - c ) > 0) {
    //            sort( end - length, end, array );
    //        }
    //    }
    //
    //    /**
    //     * Performs a sort on the given array. Elements will be re-ordered into 
    //     * ascending order.
    //     * 
    //     * @param array
    //     *            the double array to sort
    //     * 
    //     * @see #sort(double[], int, int)
    //     */
    //    public static void sort( double[] array ) {
    //
    //        sort( 0, array.length, array );
    //    }
    //
    //    /**
    //     * Performs a sort on the section of the array between the given indices.
    //     * Elements will be re-ordered into ascending order.
    //     * 
    //     * @param array
    //     *            the double array to sort
    //     * @param start
    //     *            the start index
    //     * @param end
    //     *            the end index + 1
    //     * 
    //     * @exception IllegalArgumentException
    //     *                when <code>start > end</code>
    //     * @exception ArrayIndexOutOfBoundsException
    //     *                when <code>start < 0</code> or
    //     *                <code>end > array.size()</code>
    //     * 
    //     * @see Double#compareTo(Double)
    //     */
    //    public static void sort( double[] array, int start, int end ) {
    //
    //        if (array == null) {
    //            throw new NullPointerException();
    //        }
    //        checkBounds( array.length, start, end );
    //        sort( start, end, array );
    //    }
    //
    //    private static void sort( int start, int end, double[] array ) {
    //
    //        double temp;
    //        int length = end - start;
    //        if (length < 7) {
    //            for (int i = start + 1; i < end; i++) {
    //                for (int j = i; j > start && lessThan( array[j], array[j - 1] ); j--) {
    //                    temp = array[j];
    //                    array[j] = array[j - 1];
    //                    array[j - 1] = temp;
    //                }
    //            }
    //            return;
    //        }
    //        int middle = ( start + end ) / 2;
    //        if (length > 7) {
    //            int bottom = start;
    //            int top = end - 1;
    //            if (length > 40) {
    //                length /= 8;
    //                bottom = med3( array, bottom, bottom + length, bottom + ( 2 * length ) );
    //                middle = med3( array, middle - length, middle, middle + length );
    //                top = med3( array, top - ( 2 * length ), top - length, top );
    //            }
    //            middle = med3( array, bottom, middle, top );
    //        }
    //        double partionValue = array[middle];
    //        int a, b, c, d;
    //        a = b = start;
    //        c = d = end - 1;
    //        while (true) {
    //            while (b <= c && !lessThan( partionValue, array[b] )) {
    //                if (array[b] == partionValue) {
    //                    temp = array[a];
    //                    array[a++] = array[b];
    //                    array[b] = temp;
    //                }
    //                b++;
    //            }
    //            while (c >= b && !lessThan( array[c], partionValue )) {
    //                if (array[c] == partionValue) {
    //                    temp = array[c];
    //                    array[c] = array[d];
    //                    array[d--] = temp;
    //                }
    //                c--;
    //            }
    //            if (b > c) {
    //                break;
    //            }
    //            temp = array[b];
    //            array[b++] = array[c];
    //            array[c--] = temp;
    //        }
    //        length = a - start < b - a ? a - start : b - a;
    //        int l = start;
    //        int h = b - length;
    //        while (length-- > 0) {
    //            temp = array[l];
    //            array[l++] = array[h];
    //            array[h++] = temp;
    //        }
    //        length = d - c < end - 1 - d ? d - c : end - 1 - d;
    //        l = b;
    //        h = end - length;
    //        while (length-- > 0) {
    //            temp = array[l];
    //            array[l++] = array[h];
    //            array[h++] = temp;
    //        }
    //        if (( length = b - a ) > 0) {
    //            sort( start, start + length, array );
    //        }
    //        if (( length = d - c ) > 0) {
    //            sort( end - length, end, array );
    //        }
    //    }
    //
    //    /**
    //     * Performs a sort on the given array. Elements will be re-ordered into
    //     * ascending order.
    //     * 
    //     * @param array
    //     *            the float array to sort
    //     * 
    //     * @see #sort(float[], int, int)
    //     */
    //    public static void sort( float[] array ) {
    //
    //        sort( 0, array.length, array );
    //    }
    //
    //    /**
    //     * Performs a sort on the section of the array between the given indices.
    //     * Elements will be re-ordered into ascending order.
    //     * 
    //     * @param array
    //     *            the float array to sort
    //     * @param start
    //     *            the start index
    //     * @param end
    //     *            the end index + 1
    //     * 
    //     * @exception IllegalArgumentException
    //     *                when <code>start > end</code>
    //     * @exception ArrayIndexOutOfBoundsException
    //     *                when <code>start < 0</code> or
    //     *                <code>end > array.size()</code>
    //     * 
    //     * @see Float#compareTo(Float)
    //     */
    //    public static void sort( float[] array, int start, int end ) {
    //
    //        if (array == null) {
    //            throw new NullPointerException();
    //        }
    //        checkBounds( array.length, start, end );
    //        sort( start, end, array );
    //    }
    //
    //    private static void sort( int start, int end, float[] array ) {
    //
    //        float temp;
    //        int length = end - start;
    //        if (length < 7) {
    //            for (int i = start + 1; i < end; i++) {
    //                for (int j = i; j > start && lessThan( array[j], array[j - 1] ); j--) {
    //                    temp = array[j];
    //                    array[j] = array[j - 1];
    //                    array[j - 1] = temp;
    //                }
    //            }
    //            return;
    //        }
    //        int middle = ( start + end ) / 2;
    //        if (length > 7) {
    //            int bottom = start;
    //            int top = end - 1;
    //            if (length > 40) {
    //                length /= 8;
    //                bottom = med3( array, bottom, bottom + length, bottom + ( 2 * length ) );
    //                middle = med3( array, middle - length, middle, middle + length );
    //                top = med3( array, top - ( 2 * length ), top - length, top );
    //            }
    //            middle = med3( array, bottom, middle, top );
    //        }
    //        float partionValue = array[middle];
    //        int a, b, c, d;
    //        a = b = start;
    //        c = d = end - 1;
    //        while (true) {
    //            while (b <= c && !lessThan( partionValue, array[b] )) {
    //                if (array[b] == partionValue) {
    //                    temp = array[a];
    //                    array[a++] = array[b];
    //                    array[b] = temp;
    //                }
    //                b++;
    //            }
    //            while (c >= b && !lessThan( array[c], partionValue )) {
    //                if (array[c] == partionValue) {
    //                    temp = array[c];
    //                    array[c] = array[d];
    //                    array[d--] = temp;
    //                }
    //                c--;
    //            }
    //            if (b > c) {
    //                break;
    //            }
    //            temp = array[b];
    //            array[b++] = array[c];
    //            array[c--] = temp;
    //        }
    //        length = a - start < b - a ? a - start : b - a;
    //        int l = start;
    //        int h = b - length;
    //        while (length-- > 0) {
    //            temp = array[l];
    //            array[l++] = array[h];
    //            array[h++] = temp;
    //        }
    //        length = d - c < end - 1 - d ? d - c : end - 1 - d;
    //        l = b;
    //        h = end - length;
    //        while (length-- > 0) {
    //            temp = array[l];
    //            array[l++] = array[h];
    //            array[h++] = temp;
    //        }
    //        if (( length = b - a ) > 0) {
    //            sort( start, start + length, array );
    //        }
    //        if (( length = d - c ) > 0) {
    //            sort( end - length, end, array );
    //        }
    //    }
    //
    //    /**
    //     * Performs a sort on the given array. Elements will be re-ordered into
    //     * ascending order.
    //     * 
    //     * @param array
    //     *            the int array to sort
    //     */
    //    public static void sort( int[] array ) {
    //
    //        sort( 0, array.length, array );
    //    }
    //
    //    /**
    //     * Performs a sort on the section of the array between the given indices.
    //     * Elements will be re-ordered into ascending order.
    //     * 
    //     * @param array
    //     *            the int array to sort
    //     * @param start
    //     *            the start index
    //     * @param end
    //     *            the end index + 1
    //     * 
    //     * @exception IllegalArgumentException
    //     *                when <code>start > end</code>
    //     * @exception ArrayIndexOutOfBoundsException
    //     *                when <code>start < 0</code> or
    //     *                <code>end > array.size()</code>
    //     */
    //    public static void sort( int[] array, int start, int end ) {
    //
    //        if (array == null) {
    //            throw new NullPointerException();
    //        }
    //        checkBounds( array.length, start, end );
    //        sort( start, end, array );
    //    }
    //
    //    private static void sort( int start, int end, int[] array ) {
    //
    //        int temp;
    //        int length = end - start;
    //        if (length < 7) {
    //            for (int i = start + 1; i < end; i++) {
    //                for (int j = i; j > start && array[j - 1] > array[j]; j--) {
    //                    temp = array[j];
    //                    array[j] = array[j - 1];
    //                    array[j - 1] = temp;
    //                }
    //            }
    //            return;
    //        }
    //        int middle = ( start + end ) / 2;
    //        if (length > 7) {
    //            int bottom = start;
    //            int top = end - 1;
    //            if (length > 40) {
    //                length /= 8;
    //                bottom = med3( array, bottom, bottom + length, bottom + ( 2 * length ) );
    //                middle = med3( array, middle - length, middle, middle + length );
    //                top = med3( array, top - ( 2 * length ), top - length, top );
    //            }
    //            middle = med3( array, bottom, middle, top );
    //        }
    //        int partionValue = array[middle];
    //        int a, b, c, d;
    //        a = b = start;
    //        c = d = end - 1;
    //        while (true) {
    //            while (b <= c && array[b] <= partionValue) {
    //                if (array[b] == partionValue) {
    //                    temp = array[a];
    //                    array[a++] = array[b];
    //                    array[b] = temp;
    //                }
    //                b++;
    //            }
    //            while (c >= b && array[c] >= partionValue) {
    //                if (array[c] == partionValue) {
    //                    temp = array[c];
    //                    array[c] = array[d];
    //                    array[d--] = temp;
    //                }
    //                c--;
    //            }
    //            if (b > c) {
    //                break;
    //            }
    //            temp = array[b];
    //            array[b++] = array[c];
    //            array[c--] = temp;
    //        }
    //        length = a - start < b - a ? a - start : b - a;
    //        int l = start;
    //        int h = b - length;
    //        while (length-- > 0) {
    //            temp = array[l];
    //            array[l++] = array[h];
    //            array[h++] = temp;
    //        }
    //        length = d - c < end - 1 - d ? d - c : end - 1 - d;
    //        l = b;
    //        h = end - length;
    //        while (length-- > 0) {
    //            temp = array[l];
    //            array[l++] = array[h];
    //            array[h++] = temp;
    //        }
    //        if (( length = b - a ) > 0) {
    //            sort( start, start + length, array );
    //        }
    //        if (( length = d - c ) > 0) {
    //            sort( end - length, end, array );
    //        }
    //    }
    //
    //    /**
    //     * Performs a sort on given array. Elements will be re-ordered into
    //     * ascending order.
    //     * 
    //     * @param array
    //     *            the long array to sort
    //     */
    //    public static void sort( long[] array ) {
    //
    //        sort( 0, array.length, array );
    //    }
    //
    //    /**
    //     * Performs a sort on the section of the array between the given indices.
    //     * Elements will be re-ordered into ascending order.
    //     * 
    //     * @param array
    //     *            the long array to sort
    //     * @param start
    //     *            the start index
    //     * @param end
    //     *            the end index + 1
    //     * 
    //     * @exception IllegalArgumentException
    //     *                when <code>start > end</code>
    //     * @exception ArrayIndexOutOfBoundsException
    //     *                when <code>start < 0</code> or
    //     *                <code>end > array.size()</code>
    //     */
    //    public static void sort( long[] array, int start, int end ) {
    //
    //        if (array == null) {
    //            throw new NullPointerException();
    //        }
    //        checkBounds( array.length, start, end );
    //        sort( start, end, array );
    //    }
    //
    //    private static void sort( int start, int end, long[] array ) {
    //
    //        long temp;
    //        int length = end - start;
    //        if (length < 7) {
    //            for (int i = start + 1; i < end; i++) {
    //                for (int j = i; j > start && array[j - 1] > array[j]; j--) {
    //                    temp = array[j];
    //                    array[j] = array[j - 1];
    //                    array[j - 1] = temp;
    //                }
    //            }
    //            return;
    //        }
    //        int middle = ( start + end ) / 2;
    //        if (length > 7) {
    //            int bottom = start;
    //            int top = end - 1;
    //            if (length > 40) {
    //                length /= 8;
    //                bottom = med3( array, bottom, bottom + length, bottom + ( 2 * length ) );
    //                middle = med3( array, middle - length, middle, middle + length );
    //                top = med3( array, top - ( 2 * length ), top - length, top );
    //            }
    //            middle = med3( array, bottom, middle, top );
    //        }
    //        long partionValue = array[middle];
    //        int a, b, c, d;
    //        a = b = start;
    //        c = d = end - 1;
    //        while (true) {
    //            while (b <= c && array[b] <= partionValue) {
    //                if (array[b] == partionValue) {
    //                    temp = array[a];
    //                    array[a++] = array[b];
    //                    array[b] = temp;
    //                }
    //                b++;
    //            }
    //            while (c >= b && array[c] >= partionValue) {
    //                if (array[c] == partionValue) {
    //                    temp = array[c];
    //                    array[c] = array[d];
    //                    array[d--] = temp;
    //                }
    //                c--;
    //            }
    //            if (b > c) {
    //                break;
    //            }
    //            temp = array[b];
    //            array[b++] = array[c];
    //            array[c--] = temp;
    //        }
    //        length = a - start < b - a ? a - start : b - a;
    //        int l = start;
    //        int h = b - length;
    //        while (length-- > 0) {
    //            temp = array[l];
    //            array[l++] = array[h];
    //            array[h++] = temp;
    //        }
    //        length = d - c < end - 1 - d ? d - c : end - 1 - d;
    //        l = b;
    //        h = end - length;
    //        while (length-- > 0) {
    //            temp = array[l];
    //            array[l++] = array[h];
    //            array[h++] = temp;
    //        }
    //        if (( length = b - a ) > 0) {
    //            sort( start, start + length, array );
    //        }
    //        if (( length = d - c ) > 0) {
    //            sort( end - length, end, array );
    //        }
    //    }

    /**
     * Performs a sort on the given array. Elements will be re-ordered into
     * ascending order.
     * 
     * @param array
     *            the Object array to sort
     * 
     * @exception ClassCastException
     *                when an element in the array does not implement Comparable
     *                or elements cannot be compared to each other
     */
    public static void sort( Object[] array ) {

        sort( 0, array.length, array );
    }

    /**
     * Performs a sort on the section of the array between the given indices.
     * Elements will be re-ordered into ascending order.
     * 
     * @param array
     *            the Object array to sort
     * @param start
     *            the start index
     * @param end
     *            the end index + 1
     * 
     * @exception ClassCastException
     *                when an element in the array does not implement <code>Comparable</code>
     *                or elements cannot be compared to each other
     * @exception IllegalArgumentException
     *                when <code>start > end</code>
     * @exception ArrayIndexOutOfBoundsException
     *                when <code>start < 0</code> or
     *                <code>end > array.size()</code>
     */
    public static void sort( Object[] array, int start, int end ) {

        if ( array == null ) {
            throw new NullPointerException();
        }
        checkBounds( array.length, start, end );
        sort( start, end, array );
    }

    private static void sort( int start, int end, Object[] array ) {

        int length = end - start;
        if ( length <= 0 ) {
            return;
        }
        if ( array instanceof String[] ) {
            stableStringSort( (String[]) array, start, end );
        }
        else {
            Object[] out = new Object[end];
            System.arraycopy( array, start, out, start, length );
            mergeSort( out, array, start, end );
        }
    }

    /**
     * Swaps the elements at the given indices in the array.
     * 
     * @param a -
     *            the index of one element to be swapped.
     * @param b -
     *            the index of the other element to be swapped.
     * @param arr -
     *            the array in which to swap elements.
     */
    private static void swap( int a, int b, Object[] arr ) {

        Object tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    /**
     * Performs a sort on the section of the array between the given indices
     * using a mergesort with exponential search algorithm (in which the merge
     * is performed by exponential search). n*log(n) performance is guaranteed
     * and in the average case it will be faster then any mergesort in which the
     * merge is performed by linear search.
     * 
     * @param in -
     *            the array for sorting.
     * @param out -
     *            the result, sorted array.
     * @param start
     *            the start index
     * @param end
     *            the end index + 1
     */
    ////@  SuppressWarnings("unchecked")
    private static void mergeSort( Object[] in, Object[] out, int start, int end ) {

        int len = end - start;
        // use insertion sort for small arrays
        if ( len <= SIMPLE_LENGTH ) {
            for (int i = start + 1; i < end; i++) {
                Comparable current = (Comparable) out[i];
                Object prev = out[i - 1];
                if ( current.compareTo( prev ) < 0 ) {
                    int j = i;
                    do {
                        out[j--] = prev;
                    }
                    while (j > start && current.compareTo( prev = out[j - 1] ) < 0);
                    out[j] = current;
                }
            }
            return;
        }
        int med = ( end + start ) >>> 1;
        mergeSort( out, in, start, med );
        mergeSort( out, in, med, end );

        // merging

        // if arrays are already sorted - no merge
        if ( ( (Comparable) in[med - 1] ).compareTo( in[med] ) <= 0 ) {
            System.arraycopy( in, start, out, start, len );
            return;
        }
        int r = med, i = start;

        // use merging with exponential search
        do {
            Comparable fromVal = (Comparable) in[start];
            Comparable rVal = (Comparable) in[r];
            if ( fromVal.compareTo( rVal ) <= 0 ) {
                int l_1 = find( in, rVal, -1, start + 1, med - 1 );
                int toCopy = l_1 - start + 1;
                System.arraycopy( in, start, out, i, toCopy );
                i += toCopy;
                out[i++] = rVal;
                r++;
                start = l_1 + 1;
            }
            else {
                int r_1 = find( in, fromVal, 0, r + 1, end - 1 );
                int toCopy = r_1 - r + 1;
                System.arraycopy( in, r, out, i, toCopy );
                i += toCopy;
                out[i++] = fromVal;
                start++;
                r = r_1 + 1;
            }
        }
        while (( end - r ) > 0 && ( med - start ) > 0);

        // copy rest of array
        if ( ( end - r ) <= 0 ) {
            System.arraycopy( in, start, out, i, med - start );
        }
        else {
            System.arraycopy( in, r, out, i, end - r );
        }
    }

    /**
     * Performs a sort on the section of the array between the given indices
     * using a mergesort with exponential search algorithm (in which the merge
     * is performed by exponential search). n*log(n) performance is guaranteed
     * and in the average case it will be faster then any mergesort in which the
     * merge is performed by linear search.
     * 
     * @param in -
     *            the array for sorting.
     * @param out -
     *            the result, sorted array.
     * @param start
     *            the start index
     * @param end
     *            the end index + 1
     * @param c -
     *            the comparator to determine the order of the array.
     */
    ////@  SuppressWarnings("unchecked")
    private static void mergeSort( Object[] in, Object[] out, int start, int end, Comparator c ) {

        int len = end - start;
        // use insertion sort for small arrays
        if ( len <= SIMPLE_LENGTH ) {
            for (int i = start + 1; i < end; i++) {
                Object current = out[i];
                Object prev = out[i - 1];
                if ( c.compare( prev, current ) > 0 ) {
                    int j = i;
                    do {
                        out[j--] = prev;
                    }
                    while (j > start && ( c.compare( prev = out[j - 1], current ) > 0 ));
                    out[j] = current;
                }
            }
            return;
        }
        int med = ( end + start ) >>> 1;
        mergeSort( out, in, start, med, c );
        mergeSort( out, in, med, end, c );

        // merging

        // if arrays are already sorted - no merge
        if ( c.compare( in[med - 1], in[med] ) <= 0 ) {
            System.arraycopy( in, start, out, start, len );
            return;
        }
        int r = med, i = start;

        // use merging with exponential search
        do {
            Object fromVal = in[start];
            Object rVal = in[r];
            if ( c.compare( fromVal, rVal ) <= 0 ) {
                int l_1 = find( in, rVal, -1, start + 1, med - 1, c );
                int toCopy = l_1 - start + 1;
                System.arraycopy( in, start, out, i, toCopy );
                i += toCopy;
                out[i++] = rVal;
                r++;
                start = l_1 + 1;
            }
            else {
                int r_1 = find( in, fromVal, 0, r + 1, end - 1, c );
                int toCopy = r_1 - r + 1;
                System.arraycopy( in, r, out, i, toCopy );
                i += toCopy;
                out[i++] = fromVal;
                start++;
                r = r_1 + 1;
            }
        }
        while (( end - r ) > 0 && ( med - start ) > 0);

        // copy rest of array
        if ( ( end - r ) <= 0 ) {
            System.arraycopy( in, start, out, i, med - start );
        }
        else {
            System.arraycopy( in, r, out, i, end - r );
        }
    }

    /**
     * Finds the place in the given range of specified sorted array, where the
     * element should be inserted for getting sorted array. Uses exponential
     * search algorithm.
     * 
     * @param arr -
     *            the array with already sorted range
     * 
     * @param val -
     *            object to be inserted
     * 
     * @param l -
     *            the start index
     * 
     * @param r -
     *            the end index
     * 
     * @param bnd -
     *            possible values 0,-1. "-1" - val is located at index more then
     *            elements equals to val. "0" - val is located at index less
     *            then elements equals to val.
     * 
     */
    ////@  SuppressWarnings("unchecked")
    private static int find( Object[] arr, Comparable val, int bnd, int l, int r ) {

        int m = l;
        int d = 1;
        while (m <= r) {
            if ( val.compareTo( arr[m] ) > bnd ) {
                l = m + 1;
            }
            else {
                r = m - 1;
                break;
            }
            m += d;
            d <<= 1;
        }
        while (l <= r) {
            m = ( l + r ) >>> 1;
            if ( val.compareTo( arr[m] ) > bnd ) {
                l = m + 1;
            }
            else {
                r = m - 1;
            }
        }
        return l - 1;
    }

    /**
     * Finds the place of specified range of specified sorted array, where the
     * element should be inserted for getting sorted array. Uses exponential
     * search algorithm.
     * 
     * @param arr -
     *            the array with already sorted range
     * @param val -
     *            object to be inserted
     * @param l -
     *            the start index
     * @param r -
     *            the end index
     * @param bnd -
     *            possible values 0,-1. "-1" - val is located at index more then
     *            elements equals to val. "0" - val is located at index less
     *            then elements equals to val.
     * @param c -
     *            the comparator used to compare Objects
     */
    ////@  SuppressWarnings("unchecked")
    private static int find( Object[] arr, Object val, int bnd, int l, int r, Comparator c ) {

        int m = l;
        int d = 1;
        while (m <= r) {
            if ( c.compare( val, arr[m] ) > bnd ) {
                l = m + 1;
            }
            else {
                r = m - 1;
                break;
            }
            m += d;
            d <<= 1;
        }
        while (l <= r) {
            m = ( l + r ) >>> 1;
            if ( c.compare( val, arr[m] ) > bnd ) {
                l = m + 1;
            }
            else {
                r = m - 1;
            }
        }
        return l - 1;
    }

    /*
     * returns the median index.
     */
    private static int medChar( int a, int b, int c, String[] arr, int id ) {

        int ac = charAt( arr[a], id );
        int bc = charAt( arr[b], id );
        int cc = charAt( arr[c], id );
        return ac < bc ? ( bc < cc ? b : ( ac < cc ? c : a ) ) : ( bc < cc ? ( ac < cc ? a : c ) : b );

    }

    /*
     * Returns the char value at the specified index of string or -1 if the
     * index more than the length of this string.
     */
    private static int charAt( String str, int i ) {

        if ( i >= str.length() ) {
            return -1;
        }
        return str.charAt( i );
    }

    /**
     * Copies object from one array to another array with reverse of objects
     * order. Source and destination arrays may be the same.
     * 
     * @param src -
     *            the source array.
     * @param from -
     *            starting position in the source array.
     * @param dst -
     *            the destination array.
     * @param to -
     *            starting position in the destination array.
     * @param len -
     *            the number of array elements to be copied.
     */
    private static void copySwap( Object[] src, int from, Object[] dst, int to, int len ) {

        if ( src == dst && from + len > to ) {
            int new_to = to + len - 1;
            for (; from < to; from++, new_to--, len--) {
                dst[new_to] = src[from];
            }
            for (; len > 1; from++, new_to--, len -= 2) {
                swap( from, new_to, dst );
            }

        }
        else {
            to = to + len - 1;
            for (; len > 0; from++, to--, len--) {
                dst[to] = src[from];
            }
        }
    }

    /**
     * Performs a sort on the given String array. Elements will be re-ordered into
     * ascending order.
     * 
     * @param arr -
     *            the array to sort
     * @param start -
     *            the start index
     * @param end -
     *            the end index + 1
     */
    private static void stableStringSort( String[] arr, int start, int end ) {

        stableStringSort( arr, arr, new String[end], start, end, 0 );
    }

    /**
     * Performs a sort on the given String array. Elements will be re-ordered into
     * ascending order. Uses a stable ternary quick sort algorithm.
     * 
     * @param arr -
     *            the array to sort
     * @param src -
     *            auxiliary array
     * @param dst -
     *            auxiliary array
     * @param start -
     *            the start index
     * @param end -
     *            the end index + 1
     * @param chId -
     *            index of char for current sorting
     */
    private static void stableStringSort( String[] arr, String[] src, String[] dst, int start, int end, int chId ) {

        int length = end - start;
        // use insertion sort for small arrays
        if ( length < SIMPLE_LENGTH ) {
            if ( src == arr ) {
                for (int i = start + 1; i < end; i++) {
                    String current = arr[i];
                    String prev = arr[i - 1];
                    if ( current.compareTo( prev ) < 0 ) {
                        int j = i;
                        do {
                            arr[j--] = prev;
                        }
                        while (j > start && current.compareTo( prev = arr[j - 1] ) < 0);
                        arr[j] = current;
                    }
                }
            }
            else {
                int actualEnd = end - 1;
                dst[start] = src[actualEnd--];
                for (int i = start + 1; i < end; i++, actualEnd--) {
                    String current = src[actualEnd];
                    String prev;
                    int j = i;
                    while (j > start && current.compareTo( prev = dst[j - 1] ) < 0) {
                        dst[j--] = prev;
                    }
                    dst[j] = current;
                }
            }
            return;
        }
        // Approximate median
        int s;
        int mid = start + length / 2;
        int lo = start;
        int hi = end - 1;
        if ( length > 40 ) {
            s = length / 8;
            lo = medChar( lo, lo + s, lo + s * 2, src, chId );
            mid = medChar( mid - s, mid, mid + s, src, chId );
            hi = medChar( hi, hi - s, hi - s * 2, src, chId );
        }
        mid = medChar( lo, mid, hi, src, chId );
        // median found
        // create 4 pointers <a (in star of src) ,
        // =b(in start of dst), >c (in end of dst)
        // i - current element;
        int midVal = charAt( src[mid], chId );
        int a, b, c;
        a = b = start;
        c = end - 1;
        int cmp;

        for (int i = start; i < end; i++) {
            String el = src[i];
            cmp = charAt( el, chId ) - midVal;
            if ( cmp < 0 ) {
                src[a] = el;
                a++;
            }
            else if ( cmp > 0 ) {
                dst[c] = el;
                c--;
            }
            else {
                dst[b] = el;
                b++;
            }
        }

        s = b - start;
        if ( s > 0 ) {
            if ( arr == src ) {
                System.arraycopy( dst, start, arr, a, s );
            }
            else {
                copySwap( dst, start, arr, a, s );
            }

            if ( b >= end && midVal == -1 ) {
                return;
            }
            stableStringSort( arr, arr, arr == dst ? src : dst, a, a + s, chId + 1 );
        }

        s = a - start;
        if ( s > 0 ) {
            stableStringSort( arr, src, dst, start, a, chId );
        }

        c++;
        s = end - c;
        if ( s > 0 ) {
            stableStringSort( arr, dst, src, c, end, chId );
        }
    }

    /**
     * Performs a sort on the section of the array between the given indices.
     * Elements will be re-ordered into ascending order according to the given
     * PersistableComparator.
     * 
     * @param array
     *            the Object array to sort
     * @param start
     *            the start index
     * @param end
     *            the end index + 1
     * @param comparator
     *            the PersistableComparator
     * 
     * @exception ClassCastException
     *                when elements in the array cannot be compared to each
     *                other using the PersistableComparator
     * @exception IllegalArgumentException
     *                when <code>start > end</code>
     * @exception ArrayIndexOutOfBoundsException
     *                when <code>start < 0</code> or
     *                <code>end > array.size()</code>
     */
    public static void sort( Object[] array, int start, int end, Comparator comparator ) {

        if ( array == null ) {
            throw new NullPointerException();
        }
        checkBounds( array.length, start, end );
        sort( start, end, array, comparator );
    }

    private static void sort( int start, int end, Object[] array, Comparator comparator ) {

        if ( comparator == null ) {
            sort( start, end, array );
        }
        else {
            int length = end - start;
            Object[] out = new Object[end];
            System.arraycopy( array, start, out, start, length );
            mergeSort( out, array, start, end, comparator );
        }
    }

    /**
     * Performs a sort on the given array. Elements will be re-ordered into
     * ascending order according to the given PersistableComparator.
     * 
     * @param array
     *            the Object array to sort
     * @param comparator
     *            the PersistableComparator
     * 
     * @exception ClassCastException
     *                when elements in the array cannot be compared to each
     *                other using the PersistableComparator
     */
    public static void sort( Object[] array, Comparator comparator ) {

        sort( 0, array.length, array, comparator );
    }

    public static int sum( PrimitiveNumberIterator primitiveNumberIterator ) {

        int total = 0;

        while (primitiveNumberIterator.hasNext()) {
            total += primitiveNumberIterator.nextDouble();
        }

        return total;
    }

    /**
     * Performs a sequential search for an object in an array.
     * 
     * @param arrayContainingObject - An array to look through.
     * @param objectToFind - An object to find in the array.
     * @return The index at which the object was found in the array. Returns -1 if the object could not be found in the array.
     */
    public static int linearSearch( Object[] arrayContainingObject, Object objectToFind ) {

        int indexObjectFoundAt = -1;
        for (int index = 0; index < arrayContainingObject.length; index++) {
            if ( objectToFind.equals( arrayContainingObject[index] ) ) {
                indexObjectFoundAt = index;
                index = arrayContainingObject.length;
            }
        }

        return indexObjectFoundAt;
    }

    //    /**
    //     * Performs a sort on the given array. Elements will be re-ordered into
    //     * ascending order.
    //     * 
    //     * @param array
    //     *            the short array to sort
    //     */
    //    public static void sort( short[] array ) {
    //
    //        sort( 0, array.length, array );
    //    }
    //
    //    /**
    //     * Performs a sort on the given array. Elements will be re-ordered into
    //     * ascending order.
    //     * 
    //     * @param array
    //     *            the short array to sort
    //     * @param start
    //     *            the start index
    //     * @param end
    //     *            the end index + 1
    //     * 
    //     * @exception IllegalArgumentException
    //     *                when <code>start > end</code>
    //     * @exception ArrayIndexOutOfBoundsException
    //     *                when <code>start < 0</code> or
    //     *                <code>end > array.size()</code>
    //     */
    //    public static void sort( short[] array, int start, int end ) {
    //
    //        if (array == null) {
    //            throw new NullPointerException();
    //        }
    //        checkBounds( array.length, start, end );
    //        sort( start, end, array );
    //    }
    //
    //    private static void sort( int start, int end, short[] array ) {
    //
    //        short temp;
    //        int length = end - start;
    //        if (length < 7) {
    //            for (int i = start + 1; i < end; i++) {
    //                for (int j = i; j > start && array[j - 1] > array[j]; j--) {
    //                    temp = array[j];
    //                    array[j] = array[j - 1];
    //                    array[j - 1] = temp;
    //                }
    //            }
    //            return;
    //        }
    //        int middle = ( start + end ) / 2;
    //        if (length > 7) {
    //            int bottom = start;
    //            int top = end - 1;
    //            if (length > 40) {
    //                length /= 8;
    //                bottom = med3( array, bottom, bottom + length, bottom + ( 2 * length ) );
    //                middle = med3( array, middle - length, middle, middle + length );
    //                top = med3( array, top - ( 2 * length ), top - length, top );
    //            }
    //            middle = med3( array, bottom, middle, top );
    //        }
    //        short partionValue = array[middle];
    //        int a, b, c, d;
    //        a = b = start;
    //        c = d = end - 1;
    //        while (true) {
    //            while (b <= c && array[b] <= partionValue) {
    //                if (array[b] == partionValue) {
    //                    temp = array[a];
    //                    array[a++] = array[b];
    //                    array[b] = temp;
    //                }
    //                b++;
    //            }
    //            while (c >= b && array[c] >= partionValue) {
    //                if (array[c] == partionValue) {
    //                    temp = array[c];
    //                    array[c] = array[d];
    //                    array[d--] = temp;
    //                }
    //                c--;
    //            }
    //            if (b > c) {
    //                break;
    //            }
    //            temp = array[b];
    //            array[b++] = array[c];
    //            array[c--] = temp;
    //        }
    //        length = a - start < b - a ? a - start : b - a;
    //        int l = start;
    //        int h = b - length;
    //        while (length-- > 0) {
    //            temp = array[l];
    //            array[l++] = array[h];
    //            array[h++] = temp;
    //        }
    //        length = d - c < end - 1 - d ? d - c : end - 1 - d;
    //        l = b;
    //        h = end - length;
    //        while (length-- > 0) {
    //            temp = array[l];
    //            array[l++] = array[h];
    //            array[h++] = temp;
    //        }
    //        if (( length = b - a ) > 0) {
    //            sort( start, start + length, array );
    //        }
    //        if (( length = d - c ) > 0) {
    //            sort( end - length, end, array );
    //        }
    //    }
    //
    //    /**
    //     * <p>
    //     * Creates a <code>String</code> representation of the
    //     * <code>boolean[]</code> passed. The result is surrounded by brackets (<code>&quot;[]&quot;</code>),
    //     * each element is converted to a <code>String</code> via the
    //     * {@link String#valueOf(boolean)} and separated by
    //     * <code>&quot;, &quot;</code>. If the array is <code>null</code>,
    //     * then <code>&quot;null&quot;</code> is returned.
    //     * </p>
    //     * 
    //     * @param array
    //     *            The <code>boolean</code> array to convert.
    //     * @return The <code>String</code> representation of <code>array</code>.
    //     * @since 1.5
    //     */
    //    public static String toString( boolean[] array ) {
    //
    //        if (array == null) {
    //            return "null"; //$NON-NLS-1$
    //        }
    //        if (array.length == 0) {
    //            return "[]"; //$NON-NLS-1$
    //        }
    //        StringBuffer sb = new StringBuffer( 2 + array.length * 5 );
    //        sb.append( '[' );
    //        sb.append( array[0] );
    //        for (int i = 1; i < array.length; i++) {
    //            sb.append( ", " ); //$NON-NLS-1$
    //            sb.append( array[i] );
    //        }
    //        sb.append( ']' );
    //        return sb.toString();
    //    }
    //
    //    /**
    //     * Creates a <code>String</code> representation of the <code>byte[]</code>
    //     * passed.
    //     * 
    //     * The result is surrounded by brackets (<code>&quot;[]&quot;</code>),
    //     * each element is converted to a <code>String</code> via the
    //     * {@link String#valueOf(int)} and separated by <code>&quot;, &quot;</code>.
    //     * If the array is <code>null</code>, then <code>&quot;null&quot;</code>
    //     * is returned.
    //     * 
    //     * @param array
    //     *            The <code>byte</code> array to convert.
    //     * @return The <code>String</code> representation of <code>array</code>.
    //     * @since 1.5
    //     */
    //    public static String toString( byte[] array ) {
    //
    //        if (array == null) {
    //            return "null"; //$NON-NLS-1$
    //        }
    //        if (array.length == 0) {
    //            return "[]"; //$NON-NLS-1$
    //        }
    //        StringBuffer sb = new StringBuffer( 2 + array.length * 3 );
    //        sb.append( '[' );
    //        sb.append( array[0] );
    //        for (int i = 1; i < array.length; i++) {
    //            sb.append( ", " ); //$NON-NLS-1$
    //            sb.append( array[i] );
    //        }
    //        sb.append( ']' );
    //        return sb.toString();
    //    }
    //
    //    /**
    //     * <p>
    //     * Creates a <code>String</code> representation of the <code>char[]</code>
    //     * passed. The result is surrounded by brackets (<code>&quot;[]&quot;</code>),
    //     * each element is converted to a <code>String</code> via the
    //     * {@link String#valueOf(char)} and separated by <code>&quot;, &quot;</code>.
    //     * If the array is <code>null</code>, then <code>&quot;null&quot;</code>
    //     * is returned.
    //     * </p>
    //     * 
    //     * @param array
    //     *            The <code>char</code> array to convert.
    //     * @return The <code>String</code> representation of <code>array</code>.
    //     * @since 1.5
    //     */
    //    public static String toString( char[] array ) {
    //
    //        if (array == null) {
    //            return "null"; //$NON-NLS-1$
    //        }
    //        if (array.length == 0) {
    //            return "[]"; //$NON-NLS-1$
    //        }
    //        StringBuffer sb = new StringBuffer( 2 + array.length * 2 );
    //        sb.append( '[' );
    //        sb.append( array[0] );
    //        for (int i = 1; i < array.length; i++) {
    //            sb.append( ", " ); //$NON-NLS-1$
    //            sb.append( array[i] );
    //        }
    //        sb.append( ']' );
    //        return sb.toString();
    //    }
    //
    //    /**
    //     * <p>
    //     * Creates a <code>String</code> representation of the
    //     * <code>double[]</code> passed. The result is surrounded by brackets (<code>&quot;[]&quot;</code>),
    //     * each element is converted to a <code>String</code> via the
    //     * {@link String#valueOf(double)} and separated by
    //     * <code>&quot;, &quot;</code>. If the array is <code>null</code>,
    //     * then <code>&quot;null&quot;</code> is returned.
    //     * </p>
    //     * 
    //     * @param array
    //     *            The <code>double</code> array to convert.
    //     * @return The <code>String</code> representation of <code>array</code>.
    //     * @since 1.5
    //     */
    //    public static String toString( double[] array ) {
    //
    //        if (array == null) {
    //            return "null"; //$NON-NLS-1$
    //        }
    //        if (array.length == 0) {
    //            return "[]"; //$NON-NLS-1$
    //        }
    //        StringBuffer sb = new StringBuffer( 2 + array.length * 5 );
    //        sb.append( '[' );
    //        sb.append( array[0] );
    //        for (int i = 1; i < array.length; i++) {
    //            sb.append( ", " ); //$NON-NLS-1$
    //            sb.append( array[i] );
    //        }
    //        sb.append( ']' );
    //        return sb.toString();
    //    }
    //
    //    /**
    //     * <p>
    //     * Creates a <code>String</code> representation of the
    //     * <code>float[]</code> passed. The result is surrounded by brackets (<code>&quot;[]&quot;</code>),
    //     * each element is converted to a <code>String</code> via the
    //     * {@link String#valueOf(float)} and separated by
    //     * <code>&quot;, &quot;</code>. If the array is <code>null</code>,
    //     * then <code>&quot;null&quot;</code> is returned.
    //     * </p>
    //     * 
    //     * @param array
    //     *            The <code>float</code> array to convert.
    //     * @return The <code>String</code> representation of <code>array</code>.
    //     * @since 1.5
    //     */
    //    public static String toString( float[] array ) {
    //
    //        if (array == null) {
    //            return "null"; //$NON-NLS-1$
    //        }
    //        if (array.length == 0) {
    //            return "[]"; //$NON-NLS-1$
    //        }
    //        StringBuffer sb = new StringBuffer( 2 + array.length * 5 );
    //        sb.append( '[' );
    //        sb.append( array[0] );
    //        for (int i = 1; i < array.length; i++) {
    //            sb.append( ", " ); //$NON-NLS-1$
    //            sb.append( array[i] );
    //        }
    //        sb.append( ']' );
    //        return sb.toString();
    //    }
    //
    //    /**
    //     * <p>
    //     * Creates a <code>String</code> representation of the <code>int[]</code>
    //     * passed. The result is surrounded by brackets (<code>&quot;[]&quot;</code>),
    //     * each element is converted to a <code>String</code> via the
    //     * {@link String#valueOf(int)} and separated by <code>&quot;, &quot;</code>.
    //     * If the array is <code>null</code>, then <code>&quot;null&quot;</code>
    //     * is returned.
    //     * </p>
    //     * 
    //     * @param array
    //     *            The <code>int</code> array to convert.
    //     * @return The <code>String</code> representation of <code>array</code>.
    //     * @since 1.5
    //     */
    //    public static String toString( int[] array ) {
    //
    //        if (array == null) {
    //            return "null"; //$NON-NLS-1$
    //        }
    //        if (array.length == 0) {
    //            return "[]"; //$NON-NLS-1$
    //        }
    //        StringBuffer sb = new StringBuffer( 2 + array.length * 4 );
    //        sb.append( '[' );
    //        sb.append( array[0] );
    //        for (int i = 1; i < array.length; i++) {
    //            sb.append( ", " ); //$NON-NLS-1$
    //            sb.append( array[i] );
    //        }
    //        sb.append( ']' );
    //        return sb.toString();
    //    }
    //
    //    /**
    //     * <p>
    //     * Creates a <code>String</code> representation of the <code>long[]</code>
    //     * passed. The result is surrounded by brackets (<code>&quot;[]&quot;</code>),
    //     * each element is converted to a <code>String</code> via the
    //     * {@link String#valueOf(long)} and separated by <code>&quot;, &quot;</code>.
    //     * If the array is <code>null</code>, then <code>&quot;null&quot;</code>
    //     * is returned.
    //     * </p>
    //     * 
    //     * @param array
    //     *            The <code>long</code> array to convert.
    //     * @return The <code>String</code> representation of <code>array</code>.
    //     * @since 1.5
    //     */
    //    public static String toString( long[] array ) {
    //
    //        if (array == null) {
    //            return "null"; //$NON-NLS-1$
    //        }
    //        if (array.length == 0) {
    //            return "[]"; //$NON-NLS-1$
    //        }
    //        StringBuffer sb = new StringBuffer( 2 + array.length * 4 );
    //        sb.append( '[' );
    //        sb.append( array[0] );
    //        for (int i = 1; i < array.length; i++) {
    //            sb.append( ", " ); //$NON-NLS-1$
    //            sb.append( array[i] );
    //        }
    //        sb.append( ']' );
    //        return sb.toString();
    //    }
    //
    //    /**
    //     * Creates a <code>String</code> representation of the
    //     * <code>short[]</code> passed.
    //     * 
    //     * The result is surrounded by brackets (<code>&quot;[]&quot;</code>),
    //     * each element is converted to a <code>String</code> via the
    //     * {@link String#valueOf(int)} and separated by <code>&quot;, &quot;</code>.
    //     * If the array is <code>null</code>, then <code>&quot;null&quot;</code>
    //     * is returned.
    //     * 
    //     * @param array
    //     *            The <code>short</code> array to convert.
    //     * @return The <code>String</code> representation of <code>array</code>.
    //     * @since 1.5
    //     */
    //    public static String toString( short[] array ) {
    //
    //        if (array == null) {
    //            return "null"; //$NON-NLS-1$
    //        }
    //        if (array.length == 0) {
    //            return "[]"; //$NON-NLS-1$
    //        }
    //        StringBuffer sb = new StringBuffer( 2 + array.length * 4 );
    //        sb.append( '[' );
    //        sb.append( array[0] );
    //        for (int i = 1; i < array.length; i++) {
    //            sb.append( ", " ); //$NON-NLS-1$
    //            sb.append( array[i] );
    //        }
    //        sb.append( ']' );
    //        return sb.toString();
    //    }
    //
    //    /**
    //     * <p>
    //     * Creates a <code>String</code> representation of the
    //     * <code>Object[]</code> passed. The result is surrounded by brackets (<code>&quot;[]&quot;</code>),
    //     * each element is converted to a <code>String</code> via the
    //     * {@link String#valueOf(Object)} and separated by
    //     * <code>&quot;, &quot;</code>. If the array is <code>null</code>,
    //     * then <code>&quot;null&quot;</code> is returned.
    //     * </p>
    //     * 
    //     * @param array
    //     *            The <code>Object</code> array to convert.
    //     * @return The <code>String</code> representation of <code>array</code>.
    //     * @since 1.5
    //     */
    //    public static String toString( Object[] array ) {
    //
    //        if (array == null) {
    //            return "null"; //$NON-NLS-1$
    //        }
    //        if (array.length == 0) {
    //            return "[]"; //$NON-NLS-1$
    //        }
    //        StringBuffer sb = new StringBuffer( 2 + array.length * 5 );
    //        sb.append( '[' );
    //        sb.append( array[0] );
    //        for (int i = 1; i < array.length; i++) {
    //            sb.append( ", " ); //$NON-NLS-1$
    //            sb.append( array[i] );
    //        }
    //        sb.append( ']' );
    //        return sb.toString();
    //    }
    //
    //    //TODO:ORIG
    //    //    /**
    //    //     * <p>
    //    //     * Creates a <i>"deep"</i> <code>String</code> representation of the
    //    //     * <code>Object[]</code> passed, such that if the array contains other
    //    //     * arrays, the <code>String</code> representation of those arrays is
    //    //     * generated as well.
    //    //     * </p>
    //    //     * <p>
    //    //     * If any of the elements are primitive arrays, the generation is delegated
    //    //     * to the other <code>toString</code> methods in this class. If any
    //    //     * element contains a reference to the original array, then it will be
    //    //     * represented as <code>"[...]"</code>. If an element is an
    //    //     * <code>Object[]</code>, then its representation is generated by a
    //    //     * recursive call to this method. All other elements are converted via the
    //    //     * {@link String#valueOf(Object)} method.
    //    //     * </p>
    //    //     * 
    //    //     * @param array
    //    //     *            The <code>Object</code> array to convert.
    //    //     * @return The <code>String</code> representation of <code>array</code>.
    //    //     * @since 1.5
    //    //     */
    //    //    public static String deepToString( Object[] array ) {
    //    //
    //    //        // delegate this to the recursive method
    //    //        return deepToStringImpl( array, new Object[] { array }, null );
    //    //    }
    //
    //    //TODO:ORIG
    //    //    /**
    //    //     * <p>
    //    //     * Implementation method used by {@link #deepToString(Object[])}.
    //    //     * </p>
    //    //     * 
    //    //     * @param array
    //    //     *            The <code>Object[]</code> to dive into.
    //    //     * @param original
    //    //     *            The original <code>Object[]</code>; used to test for self
    //    //     *            references.
    //    //     * @param sb
    //    //     *            The <code>StringBuffer</code> instance to append to or
    //    //     *            <code>null</code> one hasn't been created yet.
    //    //     * @return The result.
    //    //     * @see #deepToString(Object[])
    //    //     */
    //    //    private static String deepToStringImpl( Object[] array, Object[] origArrays, StringBuffer sb ) {
    //    //
    //    //        if (array == null) {
    //    //            return "null"; //$NON-NLS-1$
    //    //        }
    //    //        if (array.length == 0) {
    //    //            return "[]"; //$NON-NLS-1$
    //    //        }
    //    //
    //    //        if (sb == null) {
    //    //            sb = new StringBuffer( 2 + array.length * 5 );
    //    //        }
    //    //        sb.append( '[' );
    //    //
    //    //        for (int i = 0; i < array.length; i++) {
    //    //            if (i != 0) {
    //    //                sb.append( ", " ); //$NON-NLS-1$
    //    //            }
    //    //            // establish current element
    //    //            Object elem = array[i];
    //    //            if (elem == null) {
    //    //                // element is null
    //    //                sb.append( "null" ); //$NON-NLS-1$
    //    //            }
    //    //            else {
    //    //                // get the Class of the current element
    //    //                Class elemClass = elem.getClass();
    //    //                if (elemClass.isArray()) {
    //    //                    // element is an array type
    //    //
    //    //                    // get the declared Class of the array (element)
    //    //                    Class elemElemClass = elemClass.getComponentType();
    //    //                    if (elemElemClass.isPrimitive()) {
    //    //                        // element is a primitive array
    //    //                        if (boolean.class.equals( elemElemClass )) {
    //    //                            sb.append( toString( (boolean[]) elem ) );
    //    //                        }
    //    //                        else if (byte.class.equals( elemElemClass )) {
    //    //                            sb.append( toString( (byte[]) elem ) );
    //    //                        }
    //    //                        else if (char.class.equals( elemElemClass )) {
    //    //                            sb.append( toString( (char[]) elem ) );
    //    //                        }
    //    //                        else if (double.class.equals( elemElemClass )) {
    //    //                            sb.append( toString( (double[]) elem ) );
    //    //                        }
    //    //                        else if (float.class.equals( elemElemClass )) {
    //    //                            sb.append( toString( (float[]) elem ) );
    //    //                        }
    //    //                        else if (int.class.equals( elemElemClass )) {
    //    //                            sb.append( toString( (int[]) elem ) );
    //    //                        }
    //    //                        else if (long.class.equals( elemElemClass )) {
    //    //                            sb.append( toString( (long[]) elem ) );
    //    //                        }
    //    //                        else if (short.class.equals( elemElemClass )) {
    //    //                            sb.append( toString( (short[]) elem ) );
    //    //                        }
    //    //                        else {
    //    //                            // no other possible primitives, so we assert that
    //    //                            throw new AssertionError();
    //    //                        }
    //    //                    }
    //    //                    else {
    //    //                        // element is an Object[], so we assert that
    //    //                                assert elem instanceof Object[];
    //    //                                if (deepToStringImplContains( origArrays, elem )) {
    //    //                                    sb.append( "[...]" ); //$NON-NLS-1$
    //    //                                }
    //    //                                else {
    //    //                                    Object[] newArray = (Object[]) elem;
    //    //                                    Object[] newOrigArrays = new Object[origArrays.length + 1];
    //    //                                    System.arraycopy( origArrays, 0, newOrigArrays, 0, origArrays.length );
    //    //                                    newOrigArrays[origArrays.length] = newArray;
    //    //                                    // make the recursive call to this method
    //    //                                    deepToStringImpl( newArray, newOrigArrays, sb );
    //    //                                }
    //    //                        }
    //    //                    }
    //    //                }
    //    //                else { // element is NOT an array, just an Object
    //    //                    sb.append( array[i] );
    //    //                }
    //    //            }
    //    //        }
    //    //        sb.append( ']' );
    //    //        return sb.toString();
    //    //    }
    //
    //    /**
    //     * <p>
    //     * Utility method used to assist the implementation of
    //     * {@link #deepToString(Object[])}.
    //     * </p>
    //     * 
    //     * @param origArrays
    //     *            An array of Object[] references.
    //     * @param array
    //     *            An Object[] reference to look for in <code>origArrays</code>.
    //     * @return A value of <code>true</code> if <code>array</code> is an
    //     *         element in <code>origArrays</code>.
    //     */
    //    private static boolean deepToStringImplContains( Object[] origArrays, Object array ) {
    //
    //        if (origArrays == null || origArrays.length == 0) {
    //            return false;
    //        }
    //        for (int i = 0; i < origArrays.length; i++) {
    //            if (origArrays[i] == array) {
    //                return true;
    //            }
    //        }
    //        return false;
    //    }
}