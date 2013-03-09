/*
 * This file is part of the Illarion Game Engine.
 *
 * Copyright © 2013 - Illarion e.V.
 *
 * The Illarion Game Engine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Illarion Game Engine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Illarion Game Engine.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.illarion.engine.backend.slick;

import javax.annotation.Nonnull;

/**
 * This is the exception thrown in case the Slick Backend causes a problem.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public class SlickEngineException extends Exception {
    public SlickEngineException() {
        super();
    }

    public SlickEngineException(@Nonnull final String message) {
        super(message);
    }

    public SlickEngineException(@Nonnull final String message, @Nonnull final Throwable cause) {
        super(message, cause);
    }

    public SlickEngineException(@Nonnull final Throwable cause) {
        super(cause);
    }
}
