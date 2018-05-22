/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.geometry.euclidean.oned;

import org.apache.commons.geometry.euclidean.EuclideanVector;
import org.apache.commons.numbers.arrays.LinearCombination;

/** This class represents a vector in one-dimensional Euclidean space.
 * Instances of this class are guaranteed to be immutable.
 */
public final class Vector1D extends Cartesian1D implements EuclideanVector<Point1D, Vector1D> {

    /** Zero vector (coordinates: 0). */
    public static final Vector1D ZERO = Vector1D.of(0.0);

    /** Unit vector (coordinates: 1). */
    public static final Vector1D ONE  = Vector1D.of(1.0);

    // CHECKSTYLE: stop ConstantName
    /** A vector with all coordinates set to NaN. */
    public static final Vector1D NaN = Vector1D.of(Double.NaN);
    // CHECKSTYLE: resume ConstantName

    /** A vector with all coordinates set to positive infinity. */
    public static final Vector1D POSITIVE_INFINITY =
        Vector1D.of(Double.POSITIVE_INFINITY);

    /** A vector with all coordinates set to negative infinity. */
    public static final Vector1D NEGATIVE_INFINITY =
        Vector1D.of(Double.NEGATIVE_INFINITY);

    /** Serializable UID. */
    private static final long serialVersionUID = 1582116020164328846L;

    /** Simple constructor.
     * @param x abscissa (coordinate value)
     */
    private Vector1D(double x) {
        super(x);
    }

    /** {@inheritDoc} */
    @Override
    public Point1D asPoint() {
        return Point1D.of(this);
    }

    /** {@inheritDoc} */
    @Override
    public Vector1D getZero() {
        return ZERO;
    }

    /** {@inheritDoc} */
    @Override
    public double getNorm1() {
        return getNorm();
    }

    /** {@inheritDoc} */
    @Override
    public double getNorm() {
        return Math.abs(getX());
    }

    /** {@inheritDoc} */
    @Override
    public double getNormSq() {
        return getX() * getX();
    }

    /** {@inheritDoc} */
    @Override
    public double getNormInf() {
        return getNorm();
    }

    /** {@inheritDoc} */
    @Override
    public Vector1D add(Vector1D v) {
        return Vector1D.of(getX() + v.getX());
    }

    /** {@inheritDoc} */
    @Override
    public Vector1D add(double factor, Vector1D v) {
        return Vector1D.of(getX() + (factor * v.getX()));
    }

    /** {@inheritDoc} */
    @Override
    public Vector1D subtract(Vector1D v) {
        return Vector1D.of(getX() - v.getX());
    }

    /** {@inheritDoc} */
    @Override
    public Vector1D subtract(double factor, Vector1D v) {
        return Vector1D.of(getX() - (factor * v.getX()));
    }

    /** {@inheritDoc} */
    @Override
    public Vector1D negate() {
        return Vector1D.of(-getX());
    }

    /** {@inheritDoc} */
    @Override
    public Vector1D normalize() throws IllegalStateException {
        double s = getNorm();
        if (s == 0) {
            throw new IllegalStateException("Norm is zero");
        }
        return scalarMultiply(1.0 / s);
    }

    /** {@inheritDoc} */
    @Override
    public Vector1D scalarMultiply(double a) {
        return Vector1D.of(a * getX());
    }

    /** {@inheritDoc} */
    @Override
    public double distance1(Vector1D v) {
        return distance(v);
    }

    /** {@inheritDoc} */
    @Override
    public double distance(Vector1D v) {
        return Math.abs(v.getX() - getX());
    }

    /** {@inheritDoc} */
    @Override
    public double distanceInf(Vector1D v) {
        return distance(v);
    }

    /** {@inheritDoc} */
    @Override
    public double distanceSq(Vector1D v) {
        final double dx = v.getX() - getX();
        return dx * dx;
    }

    /** {@inheritDoc} */
    @Override
    public double dotProduct(Vector1D v) {
        return getX() * v.getX();
    }

    /**
     * Get a hashCode for the vector.
     * <p>All NaN values have the same hash code.</p>
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        if (isNaN()) {
            return 857;
        }
        return 403 * Double.hashCode(getX());
    }

    /**
     * Test for the equality of two vectors.
     * <p>
     * If all coordinates of two vectors are exactly the same, and none are
     * <code>Double.NaN</code>, the two vectors are considered to be equal.
     * </p>
     * <p>
     * <code>NaN</code> coordinates are considered to globally affect the vector
     * and be equal to each other - i.e, if either (or all) coordinates of the
     * vector are equal to <code>Double.NaN</code>, the vector is equal to
     * {@link #NaN}.
     * </p>
     *
     * @param other Object to test for equality to this
     * @return true if two vector objects are equal, false if
     *         object is null, not an instance of Vector1D, or
     *         not equal to this Vector1D instance
     *
     */
    @Override
    public boolean equals(Object other) {

        if (this == other) {
            return true;
        }

        if (other instanceof Vector1D) {
            final Vector1D rhs = (Vector1D) other;
            if (rhs.isNaN()) {
                return this.isNaN();
            }

            return getX() == rhs.getX();
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "{" + getX() + "}";
    }

    /** Returns a vector with the given coordinate value.
     * @param x vector coordinate
     * @return vector instance
     */
    public static Vector1D of(double x) {
        return new Vector1D(x);
    }

    /** Returns a vector instance with the given coordinate value.
     * @param value vector coordinate
     * @return vector instance
     */
    public static Vector1D of(Cartesian1D value) {
        return new Vector1D(value.getX());
    }

    /** Returns a vector consisting of the linear combination of the inputs.
     * <p>
     * A linear combination is the sum of all of the inputs multiplied by their
     * corresponding scale factors. All inputs are interpreted as vectors. If points
     * are to be passed, they should be viewed as representing the vector from the
     * zero point to the given point.
     * </p>
     *
     * @param a scale factor for first coordinate
     * @param c first coordinate
     * @return vector with coordinates calculated by {@code a * c}
     */
    public static Vector1D linearCombination(double a, Cartesian1D c) {
        return new Vector1D(a * c.getX());
    }

    /** Returns a vector consisting of the linear combination of the inputs.
     * <p>
     * A linear combination is the sum of all of the inputs multiplied by their
     * corresponding scale factors. All inputs are interpreted as vectors. If points
     * are to be passed, they should be viewed as representing the vector from the
     * zero point to the given point.
     * </p>
     *
     * @param a1 scale factor for first coordinate
     * @param c1 first coordinate
     * @param a2 scale factor for second coordinate
     * @param c2 second coordinate
     * @return vector with coordinates calculated by {@code (a1 * c1) + (a2 * c2)}
     */
    public static Vector1D linearCombination(double a1, Cartesian1D c1, double a2, Cartesian1D c2) {
        return new Vector1D(
                LinearCombination.value(a1, c1.getX(), a2, c2.getX()));
    }

    /** Returns a vector consisting of the linear combination of the inputs.
     * <p>
     * A linear combination is the sum of all of the inputs multiplied by their
     * corresponding scale factors. All inputs are interpreted as vectors. If points
     * are to be passed, they should be viewed as representing the vector from the
     * zero point to the given point.
     * </p>
     *
     * @param a1 scale factor for first coordinate
     * @param c1 first coordinate
     * @param a2 scale factor for second coordinate
     * @param c2 second coordinate
     * @param a3 scale factor for third coordinate
     * @param c3 third coordinate
     * @return vector with coordinates calculated by {@code (a1 * c1) + (a2 * c2) + (a3 * c3)}
     */
    public static Vector1D linearCombination(double a1, Cartesian1D c1, double a2, Cartesian1D c2,
            double a3, Cartesian1D c3) {
        return new Vector1D(
                LinearCombination.value(a1, c1.getX(), a2, c2.getX(), a3, c3.getX()));
    }

    /** Returns a vector consisting of the linear combination of the inputs.
     * <p>
     * A linear combination is the sum of all of the inputs multiplied by their
     * corresponding scale factors. All inputs are interpreted as vectors. If points
     * are to be passed, they should be viewed as representing the vector from the
     * zero point to the given point.
     * </p>
     *
     * @param a1 scale factor for first coordinate
     * @param c1 first coordinate
     * @param a2 scale factor for second coordinate
     * @param c2 second coordinate
     * @param a3 scale factor for third coordinate
     * @param c3 third coordinate
     * @param a4 scale factor for fourth coordinate
     * @param c4 fourth coordinate
     * @return point with coordinates calculated by {@code (a1 * c1) + (a2 * c2) + (a3 * c3) + (a4 * c4)}
     */
    public static Vector1D linearCombination(double a1, Cartesian1D c1, double a2, Cartesian1D c2,
            double a3, Cartesian1D c3, double a4, Cartesian1D c4) {
        return new Vector1D(
                LinearCombination.value(a1, c1.getX(), a2, c2.getX(), a3, c3.getX(), a4, c4.getX()));
    }
}
