/*
 * Copyright (c) Thomas Parker, 2012.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
 */
package pcgen.cdom.facet;

import pcgen.cdom.base.CDOMList;
import pcgen.cdom.enumeration.ObjectKey;
import pcgen.cdom.facet.base.AbstractSourcedListFacet;
import pcgen.cdom.facet.model.DomainFacet;
import pcgen.cdom.list.DomainSpellList;
import pcgen.core.Domain;
import pcgen.core.spell.Spell;

/**
 * DomainSpellListFacet tracks the Domain Spell Lists granted to a Player
 * Character by a CDOMObject. This may be a static SpellList or a choice of a
 * SpellLists available to the Player Character.
 * 
 * @author Thomas Parker (thpr [at] yahoo.com)
 */
public class DomainSpellListFacet extends
		AbstractSourcedListFacet<CDOMList<Spell>> implements
		DataFacetChangeListener<Domain>
{

	private SpellListFacet spellListFacet;

	private DomainFacet domainFacet;

	/**
	 * Adds Domain Spell Lists granted to the Player Character due to the Domain
	 * selections of the Player Character.
	 * 
	 * Triggered when one of the Facets to which DomainSpellListFacet listens
	 * fires a DataFacetChangeEvent to indicate a Domain was added to a Player
	 * Character.
	 * 
	 * @param dfce
	 *            The DataFacetChangeEvent containing the information about the
	 *            change
	 * 
	 * @see pcgen.cdom.facet.DataFacetChangeListener#dataAdded(pcgen.cdom.facet.DataFacetChangeEvent)
	 */
	@Override
	public void dataAdded(DataFacetChangeEvent<Domain> dfce)
	{
		DomainSpellList list =
				dfce.getCDOMObject().get(ObjectKey.DOMAIN_SPELLLIST);
		//list should never be null??
		spellListFacet.add(dfce.getCharID(), list, dfce.getCDOMObject());
	}

	/**
	 * Removes Domain Spell Lists (previously) granted to the Player Character
	 * due to the Domain removal from a Player Character.
	 * 
	 * Triggered when one of the Facets to which DomainSpellListFacet listens
	 * fires a DataFacetChangeEvent to indicate a Domain was removed from a
	 * Player Character.
	 * 
	 * @param dfce
	 *            The DataFacetChangeEvent containing the information about the
	 *            change
	 * 
	 * @see pcgen.cdom.facet.DataFacetChangeListener#dataRemoved(pcgen.cdom.facet.DataFacetChangeEvent)
	 */
	@Override
	public void dataRemoved(DataFacetChangeEvent<Domain> dfce)
	{
		spellListFacet.removeAll(dfce.getCharID(), dfce.getSource());
	}

	public void setSpellListFacet(SpellListFacet spellListFacet)
	{
		this.spellListFacet = spellListFacet;
	}

	public void setDomainFacet(DomainFacet domainFacet)
	{
		this.domainFacet = domainFacet;
	}

	/**
	 * Initializes the connections for DomainSpellListFacet to other facets.
	 * 
	 * This method is automatically called by the Spring framework during
	 * initialization of the DomainSpellListFacet.
	 */
	public void init()
	{
		domainFacet.addDataFacetChangeListener(this);
	}
}
