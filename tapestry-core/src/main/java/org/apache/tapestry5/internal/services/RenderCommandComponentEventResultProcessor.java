// Copyright 2007, 2008, 2010 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry5.internal.services;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.internal.services.ajax.AjaxFormUpdateController;
import org.apache.tapestry5.runtime.RenderCommand;
import org.apache.tapestry5.runtime.RenderQueue;
import org.apache.tapestry5.services.ComponentEventResultProcessor;

import java.io.IOException;

/**
 * Processor for objects that implement {@link RenderCommand} (such as
 * {@link org.apache.tapestry5.internal.structure.BlockImpl}).
 * 
 * @see AjaxPartialResponseRenderer#renderPartialPageMarkup()
 */
public class RenderCommandComponentEventResultProcessor implements ComponentEventResultProcessor<RenderCommand>
{
    private final PageRenderQueue pageRenderQueue;

    private final AjaxFormUpdateController ajaxFormUpdateController;

    private final RenderCommand setup = new RenderCommand()
    {
        public void render(MarkupWriter writer, RenderQueue queue)
        {
            ajaxFormUpdateController.setupBeforePartialZoneRender(writer);
        }
    };

    private final RenderCommand cleanup = new RenderCommand()
    {
        public void render(MarkupWriter writer, RenderQueue queue)
        {
            ajaxFormUpdateController.cleanupAfterPartialZoneRender();
        }
    };

    public RenderCommandComponentEventResultProcessor(PageRenderQueue pageRenderQueue,
            AjaxFormUpdateController ajaxFormUpdateController)
    {
        this.pageRenderQueue = pageRenderQueue;
        this.ajaxFormUpdateController = ajaxFormUpdateController;
    }

    public void processResultValue(final RenderCommand value) throws IOException
    {
        RenderCommand wrapper = new RenderCommand()
        {
            public void render(MarkupWriter writer, RenderQueue queue)
            {
                queue.push(cleanup);
                queue.push(value);
                queue.push(setup);
            }
        };

        pageRenderQueue.initializeForPartialPageRender(wrapper);
    }
}
