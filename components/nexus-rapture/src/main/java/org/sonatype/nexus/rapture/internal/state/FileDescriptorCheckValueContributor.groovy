/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2008-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.rapture.internal.state

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.goodies.common.ComponentSupport
import org.sonatype.nexus.common.system.FileDescriptorService
import org.sonatype.nexus.rapture.StateContributor

@Named
@Singleton
class FileDescriptorCheckValueContributor
    extends ComponentSupport
    implements StateContributor
{
  private final FileDescriptorService fileDescriptorService
  private final boolean disable

  @Inject
  FileDescriptorCheckValueContributor(final FileDescriptorService fileDescriptorService,
                                      final @Named('${nexus.file.descriptor.warning.disabled:-false}') boolean disable) {
    this.fileDescriptorService = fileDescriptorService;
    this.disable = disable;
  }

  @Override
  Map<String, Object> getState() {
    return [file_descriptor_limit: [
        limitOk    : disable || fileDescriptorService.fileDescriptorLimitOk,
        count      : fileDescriptorService.fileDescriptorCount,
        recommended: fileDescriptorService.fileDescriptorRecommended
    ]]
  }
}
