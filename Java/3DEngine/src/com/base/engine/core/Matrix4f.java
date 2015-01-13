package com.base.engine.core;

public class Matrix4f {
    private float[][] m;

    public Matrix4f() {
        m = new float[4][4];
    }

    public Matrix4f initIdentity() {
        //@formatter:off
        m[0][0] = 1;
        m[0][1] = 0;
        m[0][2] = 0;
        m[0][3] = 0;
        m[1][0] = 0;
        m[1][1] = 1;
        m[1][2] = 0;
        m[1][3] = 0;
        m[2][0] = 0;
        m[2][1] = 0;
        m[2][2] = 1;
        m[2][3] = 0;
        m[3][0] = 0;
        m[3][1] = 0;
        m[3][2] = 0;
        m[3][3] = 1;
        //@formatter:on

        return this;
    }

    public Matrix4f initTranslation(Vector3f pos) {
        return initTranslation(pos.getX(), pos.getY(), pos.getZ());
    }

    public Matrix4f initTranslation(float x, float y, float z) {
        //@formatter:off
        m[0][0] = 1;
        m[0][1] = 0;
        m[0][2] = 0;
        m[0][3] = x;
        m[1][0] = 0;
        m[1][1] = 1;
        m[1][2] = 0;
        m[1][3] = y;
        m[2][0] = 0;
        m[2][1] = 0;
        m[2][2] = 1;
        m[2][3] = z;
        m[3][0] = 0;
        m[3][1] = 0;
        m[3][2] = 0;
        m[3][3] = 1;
        //@formatter:on

        return this;
    }

    public Matrix4f initRotation(float x, float y, float z) {
        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();

        x = (float) Math.toRadians(x);
        y = (float) Math.toRadians(y);
        z = (float) Math.toRadians(z);

        //@formatter:off
        rz.m[0][0] = (float) MathUtil.cos(z);
        rz.m[0][1] = (float) -MathUtil.sin(z);
        rz.m[0][2] = 0;
        rz.m[0][3] = 0;
        rz.m[1][0] = (float) MathUtil.sin(z);
        rz.m[1][1] = (float) MathUtil.cos(z);
        rz.m[1][2] = 0;
        rz.m[1][3] = 0;
        rz.m[2][0] = 0;
        rz.m[2][1] = 0;
        rz.m[2][2] = 1;
        rz.m[2][3] = 0;
        rz.m[3][0] = 0;
        rz.m[3][1] = 0;
        rz.m[3][2] = 0;
        rz.m[3][3] = 1;

        rx.m[0][0] = 1;
        rx.m[0][1] = 0;
        rx.m[0][2] = 0;
        rx.m[0][3] = 0;
        rx.m[1][0] = 0;
        rx.m[1][1] = (float) MathUtil.cos(x);
        rx.m[1][2] = (float) -MathUtil.sin(x);
        rx.m[1][3] = 0;
        rx.m[2][0] = 0;
        rx.m[2][1] = (float) MathUtil.sin(x);
        rx.m[2][2] = (float) MathUtil.cos(x);
        rx.m[2][3] = 0;
        rx.m[3][0] = 0;
        rx.m[3][1] = 0;
        rx.m[3][2] = 0;
        rx.m[3][3] = 1;

        ry.m[0][0] = (float) MathUtil.cos(y);
        ry.m[0][1] = 0;
        ry.m[0][2] = (float) MathUtil.sin(y);
        ry.m[0][3] = 0;
        ry.m[1][0] = 0;
        ry.m[1][1] = 1;
        ry.m[1][2] = 0;
        ry.m[1][3] = 0;
        ry.m[2][0] = (float) -MathUtil.sin(y);
        ry.m[2][1] = 0;
        ry.m[2][2] = (float) MathUtil.cos(y);
        ry.m[2][3] = 0;
        ry.m[3][0] = 0;
        ry.m[3][1] = 0;
        ry.m[3][2] = 0;
        ry.m[3][3] = 1;
        //@formatter:on

        m = rz.mul(ry.mul(rx)).getM();

        return this;
    }

    public Matrix4f initScale(float x, float y, float z) {
        //@formatter:off
        m[0][0] = x;        m[0][1] = 0;        m[0][2] = 0;        m[0][3] = 0;
        m[1][0] = 0;        m[1][1] = y;        m[1][2] = 0;        m[1][3] = 0;
        m[2][0] = 0;        m[2][1] = 0;        m[2][2] = z;        m[2][3] = 0;
        m[3][0] = 0;        m[3][1] = 0;        m[3][2] = 0;        m[3][3] = 1;
        //@formatter:on

        return this;
    }

    public Matrix4f initPerspective(float fov, float aspectRatio, float zNear, float zFar) {
        float ar = aspectRatio;
        float tanHalfFOV = (float) Math.tan(fov / 2);
        float zRange = zNear - zFar;

        //@formatter:off
        m[0][0] = 1.0f / (tanHalfFOV * ar);
        m[0][1] = 0;
        m[0][2] = 0;
        m[0][3] = 0;
        m[1][0] = 0;
        m[1][1] = 1.0f / tanHalfFOV;
        m[1][2] = 0;
        m[1][3] = 0;
        m[2][0] = 0;
        m[2][1] = 0;
        m[2][2] = (-zNear - zFar) / zRange;
        m[2][3] = 2 * zFar * zNear / zRange;
        m[3][0] = 0;
        m[3][1] = 0;
        m[3][2] = 1;
        m[3][3] = 0;
        //@formatter:on

        return this;
    }

    public Matrix4f initOrthographic(float left, float right, float bottom, float top, float near, float far) {
        float width = right - left;
        float height = top - bottom;
        float depth = far - near;

        //@formatter:off
        m[0][0] = 2 / width;
        m[0][1] = 0;
        m[0][2] = 0;
        m[0][3] = -(right + left) / width;
        m[1][0] = 0;
        m[1][1] = 2 / height;
        m[1][2] = 0;
        m[1][3] = -(top + bottom) / height;
        m[2][0] = 0;
        m[2][1] = 0;
        m[2][2] = -2 / depth;
        m[2][3] = -(far + near) / depth;
        m[3][0] = 0;
        m[3][1] = 0;
        m[3][2] = 0;
        m[3][3] = 1;
        //@formatter:on

        return this;
    }

    public Matrix4f initRotation(Vector3f forward, Vector3f up) {
        Vector3f f = forward.normalized();

        Vector3f r = up.normalized();
        r = r.cross(f);

        Vector3f u = f.cross(r);

        return initRotation(f, u, r);
    }

    public Matrix4f initRotation(Vector3f forward, Vector3f up, Vector3f right) {
        Vector3f f = forward;
        Vector3f r = right;
        Vector3f u = up;

        m[0][0] = r.getX();
        m[0][1] = r.getY();
        m[0][2] = r.getZ();
        m[0][3] = 0;
        m[1][0] = u.getX();
        m[1][1] = u.getY();
        m[1][2] = u.getZ();
        m[1][3] = 0;
        m[2][0] = f.getX();
        m[2][1] = f.getY();
        m[2][2] = f.getZ();
        m[2][3] = 0;
        m[3][0] = 0;
        m[3][1] = 0;
        m[3][2] = 0;
        m[3][3] = 1;

        return this;
    }

    public Vector3f transform(Vector3f r) {
//				@formatter:off
        return new Vector3f(m[0][0] * r.getX() + m[0][1] * r.getY() + m[0][2] * r.getZ() + m[0][3],
                m[1][0] * r.getX() + m[1][1] * r.getY() + m[1][2] * r.getZ() + m[1][3],
                m[2][0] * r.getX() + m[2][1] * r.getY() + m[2][2] * r.getZ() + m[2][3]);
//				@formatter:on
    }

    public Matrix4f mul(Matrix4f r) {
        Matrix4f res = new Matrix4f();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
//				@formatter:off
                res.set(i, j, m[i][0] * r.get(0, j) +
                        m[i][1] * r.get(1, j) +
                        m[i][2] * r.get(2, j) +
                        m[i][3] * r.get(3, j));
//				@formatter:on
            }
        }

        return res;
    }

    public float get(int x, int y) {
        return m[x][y];
    }

    public void set(int x, int y, float val) {
        m[x][y] = val;
    }

    public float[][] getM() {
        float[][] res = new float[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                res[i][j] = m[i][j];
            }
        }

        return res;
    }

    public Matrix4f mul(float scalar) {
        m[0][0] *= scalar;
        m[0][1] *= scalar;
        m[0][2] *= scalar;
        m[0][3] *= scalar;
        m[1][0] *= scalar;
        m[1][1] *= scalar;
        m[1][2] *= scalar;
        m[1][3] *= scalar;
        m[2][0] *= scalar;
        m[2][1] *= scalar;
        m[2][2] *= scalar;
        m[2][3] *= scalar;
        m[3][0] *= scalar;
        m[3][1] *= scalar;
        m[3][2] *= scalar;
        m[3][3] *= scalar;
        return this;
    }

    public Matrix4f invert() {
        return invert(null);
    }

    public Matrix4f invert(Matrix4f store) {
        if (store == null) {
            store = new Matrix4f();
        }

        float fA0 = m[0][0] * m[1][1] - m[0][1] * m[1][0];
        float fA1 = m[0][0] * m[1][2] - m[0][2] * m[1][0];
        float fA2 = m[0][0] * m[1][3] - m[0][3] * m[1][0];
        float fA3 = m[0][1] * m[1][2] - m[0][2] * m[1][1];
        float fA4 = m[0][1] * m[1][3] - m[0][3] * m[1][1];
        float fA5 = m[0][2] * m[1][3] - m[0][3] * m[1][2];
        float fB0 = m[2][0] * m[3][1] - m[2][1] * m[3][0];
        float fB1 = m[2][0] * m[3][2] - m[2][2] * m[3][0];
        float fB2 = m[2][0] * m[3][3] - m[2][3] * m[3][0];
        float fB3 = m[2][1] * m[3][2] - m[2][2] * m[3][1];
        float fB4 = m[2][1] * m[3][3] - m[2][3] * m[3][1];
        float fB5 = m[2][2] * m[3][3] - m[2][3] * m[3][2];
        float fDet = fA0 * fB5 - fA1 * fB4 + fA2 * fB3 + fA3 * fB2 - fA4 * fB1 + fA5 * fB0;

        if (Math.abs(fDet) <= 0f) {
            throw new ArithmeticException("This matrix cannot be inverted");
        }

        store.m[0][0] = +m[1][1] * fB5 - m[1][2] * fB4 + m[1][3] * fB3;
        store.m[1][0] = -m[1][0] * fB5 + m[1][2] * fB2 - m[1][3] * fB1;
        store.m[2][0] = +m[1][0] * fB4 - m[1][1] * fB2 + m[1][3] * fB0;
        store.m[3][0] = -m[1][0] * fB3 + m[1][1] * fB1 - m[1][2] * fB0;
        store.m[0][1] = -m[0][1] * fB5 + m[0][2] * fB4 - m[0][3] * fB3;
        store.m[1][1] = +m[0][0] * fB5 - m[0][2] * fB2 + m[0][3] * fB1;
        store.m[2][1] = -m[0][0] * fB4 + m[0][1] * fB2 - m[0][3] * fB0;
        store.m[3][1] = +m[0][0] * fB3 - m[0][1] * fB1 + m[0][2] * fB0;
        store.m[0][2] = +m[3][1] * fA5 - m[3][2] * fA4 + m[3][3] * fA3;
        store.m[1][2] = -m[3][0] * fA5 + m[3][2] * fA2 - m[3][3] * fA1;
        store.m[2][2] = +m[3][0] * fA4 - m[3][1] * fA2 + m[3][3] * fA0;
        store.m[3][2] = -m[3][0] * fA3 + m[3][1] * fA1 - m[3][2] * fA0;
        store.m[0][3] = -m[2][1] * fA5 + m[2][2] * fA4 - m[2][3] * fA3;
        store.m[1][3] = +m[2][0] * fA5 - m[2][2] * fA2 + m[2][3] * fA1;
        store.m[2][3] = -m[2][0] * fA4 + m[2][1] * fA2 - m[2][3] * fA0;
        store.m[3][3] = +m[2][0] * fA3 - m[2][1] * fA1 + m[2][2] * fA0;

        float fInvDet = 1.0f / fDet;
        store.mul(fInvDet);

        return store;
    }

    public void setM(float[][] m) {
        this.m = m;
    }
}
