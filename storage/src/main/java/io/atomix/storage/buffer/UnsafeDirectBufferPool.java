/*
 * Copyright 2015-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.atomix.storage.buffer;

import io.atomix.utils.concurrent.ReferenceFactory;
import io.atomix.utils.concurrent.ReferenceManager;

/**
 * Direct buffer pool.
 *
 * @author <a href="http://github.com/kuujo">Jordan Halterman</a>
 */
public class UnsafeDirectBufferPool extends BufferPool {
  public UnsafeDirectBufferPool() {
    super(new DirectBufferFactory());
  }

  @Override
  public void release(Buffer reference) {
    reference.rewind();
    super.release(reference);
  }

  /**
   * Direct buffer factory.
   */
  private static class DirectBufferFactory implements ReferenceFactory<Buffer> {
    @Override
    public Buffer createReference(ReferenceManager<Buffer> manager) {
      UnsafeDirectBuffer buffer = new UnsafeDirectBuffer(UnsafeDirectBytes.allocate(1024), manager);
      buffer.reset(0, 1024, Integer.MAX_VALUE);
      return buffer;
    }
  }

}
