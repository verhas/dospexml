#! /usr/bin/python

from pyama.configuration import Configuration
from pyama.linenumberer import LineNumberer
from pyama.lineskipperhandler import LineSkipper
from pyama.processor import Processor
from pyama.regexhandler import RegexHandler
from pyama.snippet import MdSnippetWriter, SnippetReader, SnippetMacro

snippetWriter = MdSnippetWriter()

ADOC = Configuration().file(r".*\.adoc$").handler(snippetWriter, RegexHandler(),SnippetReader(), LineSkipper(), LineNumberer(runpass=[4]))
#SEGMENT = Configuration().file(r".*\.adoc.jam$").handler(RegexHandler())
JAVA = Configuration().file(r".*\.java$").handler(SnippetReader(), SnippetMacro())
OUT = Configuration().file(r"generated/output/.*\.txt$").handler(SnippetReader(), SnippetMacro())
configs = [ADOC, JAVA, OUT] #, SEGMENT]

Processor(configs, "**/*.*").process()

