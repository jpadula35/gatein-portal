/*
 * Copyright (C) 2011 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.exoplatform.portal.config;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.container.component.RequestLifeCycle;
import org.exoplatform.portal.config.model.Application;
import org.exoplatform.portal.config.model.Container;
import org.exoplatform.portal.config.model.PortalConfig;
import org.exoplatform.portal.mop.importer.ImportMode;
import org.exoplatform.portal.mop.page.PageContext;
import org.exoplatform.portal.mop.page.PageKey;
import org.exoplatform.portal.mop.page.PageService;
import org.exoplatform.portal.pom.spi.portlet.Portlet;

/**
 * @author <a href="trongtt@gmail.com">Trong Tran</a>
 * @version $Revision$
 */
public class TestSiteDataImportInsert extends AbstractSiteDataImportTest
{
   
   @Override
   protected ImportMode getMode()
   {
      return ImportMode.INSERT;
   }
   
   @Override
   protected void afterSecondBootWithOverride(PortalContainer container) throws Exception
   {
      //
      RequestLifeCycle.begin(container);
      
      DataStorage dataStorage = (DataStorage)container.getComponentInstanceOfType(DataStorage.class);
      PageService pageService = (PageService)container.getComponentInstanceOfType(PageService.class);
      PortalConfig portal = dataStorage.getPortalConfig("classic");
      Container layout = portal.getPortalLayout();
      assertEquals(1, layout.getChildren().size());
      Application<Portlet> layoutPortlet = (Application<Portlet>)layout.getChildren().get(0);
      assertEquals("site1/layout", dataStorage.getId(layoutPortlet.getState()));
      
      //
      PageContext home = pageService.loadPage(PageKey.parse("portal::classic::home"));
      assertNotNull(home);
      assertEquals("site 1", home.getState().getDisplayName());

      PageContext page1 = pageService.loadPage(PageKey.parse("portal::classic::page1"));
      assertNotNull(page1);
      assertEquals("site 1", page1.getState().getDisplayName());
      
      PageContext page2 = pageService.loadPage(PageKey.parse("portal::classic::page2"));
      assertNotNull(page2);
      assertEquals("site 2", page2.getState().getDisplayName());
      
      PageContext dashboard1 = pageService.loadPage(PageKey.parse("user::root::dashboard1"));
      assertNotNull(dashboard1);
      assertEquals("site 2", dashboard1.getState().getDisplayName());
      
      RequestLifeCycle.end();    
   }
}
