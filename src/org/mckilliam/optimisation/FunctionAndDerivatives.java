/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mckilliam.optimisation;

import Jama.Matrix;

/**
 * Includes a Hessian and
 * @author Robby McKilliam
 */
public interface FunctionAndDerivatives extends MultivariateFunction{

    @Override
    double value(Matrix x);

    /** Return the Hessian matrix calculated at x */
    Matrix hessian(Matrix x);

    /** Return the gradient matrix calculated at x */
    Matrix gradient(Matrix x);

}
